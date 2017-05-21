package com.zy.dropwizard.utils.migrations;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.migration.Change;
import org.apache.ibatis.migration.FileMigrationLoader;
import org.apache.ibatis.migration.MigrationException;
import org.apache.ibatis.migration.MigrationLoader;

import java.io.File;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarFile;

/**
 * Component:
 * Description:
 * Date: 17/5/16
 *
 * @author yue.zhang
 */
@Slf4j
public class ComplexMigrationLoader implements MigrationLoader {

    private String migrationPath;
    private Enumeration<URL> paths;
    private List<Change> changes;
    private Map<BigDecimal,MigrationLoader> migrationLoaderMap;

    public ComplexMigrationLoader(String migrationPath , Enumeration<URL> paths){
        this.migrationPath = migrationPath;
        this.paths = paths;
        this.changes = new ArrayList<>();
        this.migrationLoaderMap = new HashMap<>();
    }

    public void init() throws Exception{
        if(paths == null){
            return;
        }

        while (paths.hasMoreElements()){
            URL dirURL = paths.nextElement();
            String dir = dirURL.getPath();
            MigrationLoader migrationLoader = null;
            if("file".equals(dirURL.getProtocol())){
                File migrationFile = new File(dirURL.toURI());
                migrationLoader = new FileMigrationLoader(migrationFile,"UTF-8",null);
            }else if("jar".equals(dirURL.getProtocol())){
                String jarPath = dir.substring(5,dir.indexOf("!"));
                JarFile jar = new JarFile(URLDecoder.decode(jarPath,"UTF-8"));
                migrationLoader = new JarMigrationLoader(jar,migrationPath,"UTF-8",null);
            }

            List<Change> temp = null;
            if (null != migrationLoader){
                temp = migrationLoader.getMigrations();
            }
            if(null == temp || temp.isEmpty()){
                log.debug("no migration script found in dir {}",dir);
                continue;
            }
            log.debug("found {} migration script in dir {}",temp.size(),dir);
            for(Change c : temp){
                if (migrationLoaderMap.containsKey(c.getId())){
                    String msg = String.format("the id of migration script in dir %s already exists, id is %s, filename is %s", dir, c.getId(), c.getFilename());
                    throw new MigrationException(msg);
                }
                log.debug("found migration script in dir {}, id is {}, filename is {}", dir, c.getId(), c.getFilename());
                changes.add(c);
                migrationLoaderMap.put(c.getId(),migrationLoader);
            }
        }
        log.debug("migration script total size is {}", changes.size());
    }


    @Override
    public List<Change> getMigrations() {
        return changes;
    }

    @Override
    public Reader getScriptReader(Change change, boolean undo) {
        MigrationLoader loader = migrationLoaderMap.get(change.getId());
        if(null == loader){
            throw new MigrationException(String.format("not found migration loader for change %s", change.getId()));
        }
        return loader.getScriptReader(change,undo);
    }

    @Override
    public Reader getBootstrapReader() {
        List<Reader> readers = new ArrayList<>();
        Reader reader;
        for (Map.Entry<BigDecimal, MigrationLoader> etr : migrationLoaderMap.entrySet()){
            reader = etr.getValue().getBootstrapReader();
            if(null != reader){
                readers.add(reader);
            }
        }
        if(readers.size() == 1){
            return readers.get(0);
        }
        if (readers.size() > 1){
            throw new MigrationException(String.format("found more than 1 readers for bootstrap, count is %s", readers.size()));
        }

        return null;
    }

    @Override
    public Reader getOnAbortReader() {
        return null;
    }
}
