package com.zy.dropwizard.utils;

import io.dropwizard.configuration.ConfigurationSourceProvider;
import io.dropwizard.configuration.FileConfigurationSourceProvider;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;

import java.io.IOException;
import java.io.InputStream;

/**
 * Component: 用来加载classpath里的文件（xxx.yml）
 * Description:
 * Date: 17/5/14
 *
 * @author yue.zhang
 */
public class ClasspathOrFileConfigurationSourceProvider implements ConfigurationSourceProvider {

    private ResourceConfigurationSourceProvider classpathProvider = new ResourceConfigurationSourceProvider();
    private FileConfigurationSourceProvider fileProvider = new FileConfigurationSourceProvider();

    @Override
    public InputStream open(String path) throws IOException {
        InputStream is = classpathProvider.open(path);
        if(is == null){
            is = fileProvider.open(path);
        }
        return is;
    }
}
