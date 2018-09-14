package com.jonatasmelo.orderlistapp.utils;

import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

import javax.servlet.ServletContext;

@RewriteConfiguration
public class RewriteAppConfigurationProvider extends HttpConfigurationProvider {
    @Override
    public Configuration getConfiguration(ServletContext servletContext) {
        return ConfigurationBuilder.begin()
                .addRule(Join.path("/").to("/index.jsf"))
                .addRule(Join.path("/orders").to("/orders.jsf"));
    }

    @Override
    public int priority() {
        return 10;
    }
}
