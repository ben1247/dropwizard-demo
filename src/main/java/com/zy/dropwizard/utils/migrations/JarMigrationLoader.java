package com.zy.dropwizard.utils.migrations;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.migration.Change;
import org.apache.ibatis.migration.MigrationException;
import org.apache.ibatis.migration.MigrationLoader;
import org.apache.ibatis.migration.MigrationReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Component:
 * Description:
 * Date: 17/5/17
 *
 * @author yue.zhang
 */
@Slf4j
public class JarMigrationLoader implements MigrationLoader {

    private final JarFile jar;
    private final String path;
    private final String charset;
    private final Properties variables;

    public JarMigrationLoader(JarFile jar, String path, String charset, Properties variables) {
        super();
        this.jar = jar;
        this.path = path;
        this.charset = charset;
        this.variables = variables;
    }

    @Override
    public List<Change> getMigrations() {
        List<Change> migrations = new ArrayList<>();
        Enumeration<JarEntry> resources = jar.entries();
        if(null != resources){
            List<JarEntry> resourceList = Collections.list(resources);
            List<String> resourceDist = new ArrayList<>();
            for(JarEntry jarEntry : resourceList){
                String entryName = jarEntry.getName();
                if(entryName.startsWith(path)
                        && entryName.endsWith(".sql")
                        && !entryName.endsWith("create_changelog.sql")
                        && !"bootstrap".equals(entryName)){
                    log.info("entryName: {}",entryName);
                    resourceDist.add(entryName.substring(entryName.lastIndexOf("/") + 1));
                }
            }

            String [] filenames = resourceDist.toArray(new String[]{});
            Arrays.sort(filenames); // 排序的目的是顺序执行sql语句
            for(String filename : filenames){
                Change change = parseChangeFromFilename(filename);
                migrations.add(change);
            }

        }
        return migrations;
    }

    private Change parseChangeFromFilename(String jarEntryName) {
        try {
            Change change = new Change();
            String [] parts = jarEntryName.split("\\.")[0].split("_");
            change.setId(new BigDecimal(parts[0]));
            StringBuilder builder = new StringBuilder();
            for(int i = 1; i < parts.length; i++){
                if(i > 1){
                    builder.append(" ");
                }
                builder.append(parts[i]);
            }
            change.setDescription(builder.toString());
            change.setFilename(jarEntryName);
            return change;
        }catch (Exception e){
            log.error("Error parse change from file. Cause: {}" , e.getMessage() ,e);
            throw new MigrationException("Error parse change from file. Cause: " + e.getMessage() ,e);
        }

    }

    @Override
    public Reader getScriptReader(Change change, boolean undo) {
        try {
            InputStream inputStream = jar.getInputStream(jar.getJarEntry(path + change.getFilename()));
            return new MigrationReader(inputStream,charset,undo,variables);
        } catch (IOException e){
            throw new MigrationException("Error reading " + change.getFilename() , e);
        }

    }

    @Override
    public Reader getBootstrapReader() {
        return null;
    }

    @Override
    public Reader getOnAbortReader() {
        return null;
    }
}
