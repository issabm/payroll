package com.issa.payroll.config;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.redisson.Redisson;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;

    @Bean
    public javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration(JHipsterProperties jHipsterProperties) {
        MutableConfiguration<Object, Object> jcacheConfig = new MutableConfiguration<>();

        URI redisUri = URI.create(jHipsterProperties.getCache().getRedis().getServer()[0]);

        Config config = new Config();
        if (jHipsterProperties.getCache().getRedis().isCluster()) {
            ClusterServersConfig clusterServersConfig = config
                .useClusterServers()
                .setMasterConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                .addNodeAddress(jHipsterProperties.getCache().getRedis().getServer());

            if (redisUri.getUserInfo() != null) {
                clusterServersConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        } else {
            SingleServerConfig singleServerConfig = config
                .useSingleServer()
                .setConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
                .setConnectionMinimumIdleSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                .setAddress(jHipsterProperties.getCache().getRedis().getServer()[0]);

            if (redisUri.getUserInfo() != null) {
                singleServerConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        }
        jcacheConfig.setStatisticsEnabled(true);
        jcacheConfig.setExpiryPolicyFactory(
            CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, jHipsterProperties.getCache().getRedis().getExpiration()))
        );
        return RedissonConfiguration.fromInstance(Redisson.create(config), jcacheConfig);
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cm) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cm);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer(javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
        return cm -> {
            createCache(cm, com.issa.payroll.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            createCache(cm, com.issa.payroll.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.User.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Authority.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Employe.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.PalierImpo.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Conjoint.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Enfant.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Contact.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Frequence.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Sens.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.ModeCalcul.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Concerne.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.TypeContrat.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.SousTypeContrat.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.NiveauScolaire.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.SituationFamiliale.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.TypeIdentite.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Identite.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.NatureAdhesion.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Situation.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Sexe.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.NatureAbsence.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.SoldeAbsence.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.SoldeAbsenceContrat.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.NatureAbsenceRebrique.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Emploi.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Category.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Echlon.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.TypeHandicap.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Groupe.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Entreprise.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Affiliation.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Devise.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Carriere.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Contrat.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Adhesion.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.EntityAdhesion.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Affectation.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Pays.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.MatricePaie.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.MatricePaieEmp.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Famille.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Rebrique.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Assiete.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.RebriqueContrat.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.AssieteInfo.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.SoldeAbsencePaie.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.PayrollInfo.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.ManagementResource.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.FormPaie.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.OperatorForm.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.FormPaieLigne.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.FormPaieLigneReb.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.ManagementResourceProfile.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.Payroll.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.MouvementPaie.class.getName(), jcacheConfiguration);
            createCache(cm, com.issa.payroll.domain.UserLog.class.getName(), jcacheConfiguration);
            // jhipster-needle-redis-add-entry
        };
    }

    private void createCache(
        javax.cache.CacheManager cm,
        String cacheName,
        javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
    ) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
