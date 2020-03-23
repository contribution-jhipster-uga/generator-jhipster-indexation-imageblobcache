const chalk = require('chalk');
const semver = require('semver');
const BaseGenerator = require('generator-jhipster/generators/generator-base');
const jhipsterConstants = require('generator-jhipster/generators/generator-constants');
const packagejs = require('../../package.json');
const fs = require('fs');

module.exports = class extends BaseGenerator {
    get initializing() {
        return {
            init(args) {
                if (args === 'default') {
                    // do something when argument is 'default'
                    this.message = 'default message';
                }
            },
            readConfig() {
                this.jhipsterAppConfig = this.getAllJhipsterConfig();
                if (!this.jhipsterAppConfig) {
                    this.error('Cannot read .yo-rc.json');
                }
            },
            displayLogo() {
                // it's here to show that you can use functions from generator-jhipster
                // this function is in: generator-jhipster/generators/generator-base.js
                this.printJHipsterLogo();

                // Have Yeoman greet the user.
                this.log(
                    `\nWelcome to the ${chalk.bold.yellow('JHipster imageBlobIndexation')} generator! ${chalk.yellow(
                        `v${packagejs.version}\n`
                    )}`
                );
            },
            checkJhipster() {
                const currentJhipsterVersion = this.jhipsterAppConfig.jhipsterVersion;
                const minimumJhipsterVersion = packagejs.dependencies['generator-jhipster'];
                if (!semver.satisfies(currentJhipsterVersion, minimumJhipsterVersion)) {
                    this.warning(
                        `\nYour generated project used an old JHipster version (${currentJhipsterVersion})... you need at least (${minimumJhipsterVersion})\n`
                    );
                }
            }
        };
    }

    prompting() {
        const prompts = [
            {
                when: () => typeof this.message === 'undefined',
                type: 'input',
                name: 'message',
                message: 'Please put something',
                default: 'hello world!'
            }
        ];

        const done = this.async();
        this.prompt(prompts).then(answers => {
            this.promptAnswers = answers;
            // To access props answers use this.promptAnswers.someOption;
            done();
        });
    }

    writing() {
        // read config from .yo-rc.json
        this.baseName = this.jhipsterAppConfig.baseName;
        this.packageName = this.jhipsterAppConfig.packageName;
        this.packageFolder = this.jhipsterAppConfig.packageFolder;
        this.clientFramework = this.jhipsterAppConfig.clientFramework;
        this.clientPackageManager = this.jhipsterAppConfig.clientPackageManager;
        this.buildTool = this.jhipsterAppConfig.buildTool;

        // use function in generator-base.js from generator-jhipster
        this.angularAppName = this.getAngularAppName();

        // use constants from generator-constants.js
        const javaDir = `${jhipsterConstants.SERVER_MAIN_SRC_DIR + this.packageFolder}/`;
        const resourceDir = jhipsterConstants.SERVER_MAIN_RES_DIR;
        const webappDir = jhipsterConstants.CLIENT_MAIN_SRC_DIR;

        // variable from questions
        if (typeof this.message === 'undefined') {
            this.message = this.promptAnswers.message;
        }

        if (isElasticsearch()) {
            this.info('Application with Elasticsearch');
        } else {
            this.error('Application dont have Elasticsearch');
        }
        // needles variables for imports and package in file;
        const needle_import = '//<--! import -->';
        const needle_package = '//<--! package -->';

        // show all variables
        this.log('\n--- some config read from config ---');
        this.log(`baseName=${this.baseName}`);
        this.log(`packageName=${this.packageName}`);
        this.log(`clientFramework=${this.clientFramework}`);
        this.log(`clientPackageManager=${this.clientPackageManager}`);
        this.log(`buildTool=${this.buildTool}`);

        this.log('\n--- some function ---');
        this.log(`angularAppName=${this.angularAppName}`);

        this.log('\n--- some const ---');
        this.log(`javaDir=${javaDir}`);
        this.log(`resourceDir=${resourceDir}`);
        this.log(`webappDir=${webappDir}`);

        this.log('\n--- variables from questions ---');
        this.log(`message=${this.message}`);
        this.log('------\n');

        if (this.clientFramework === 'react') {
            // No front-end modification.
        }
        if (this.clientFramework === 'angularX') {
            // app/entities/photo-Photo
            this.fs.copy(
                this.templatePath('src/main/webapp/app/entities/photo-photo'),
                this.destinationPath(webappDir + 'app/entities/photo-photo')
            );

            // app/shared/model/photo-photo.model.ts
            this.fs.copy(
                this.templatePath('src/main/webapp/app/shared/model/photo-photo.model.ts'),
                this.destinationPath(webappDir + 'app/shared/model/photo-photo.model.ts')
            );

            // add entity to menu
            this.addEntityToMenu('photo', false, this.clientFramework);

            // add entity to module
            this.rewriteFile(
                webappDir + 'app/entities/entity.module.ts',
                '/* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */',
                "{path: 'photo',loadChildren: () => import('./photo-photo/photo-photo.module').then(m => m.LazyblobPhotoPhotoModule)},"
            );

            // app/entities/photo-photo/photo-photo.module.ts
            var baseNameUpperCase = this.baseName;
            baseNameUpperCase = baseNameUpperCase.cleanup();

            this.rewriteFile(
                webappDir + 'app/entities/photo-photo/photo-photo.module.ts',
                needle_import,
                'import { ' + baseNameUpperCase + "SharedModule } from 'app/shared/shared.module';"
            );
            this.rewriteFile(
                webappDir + 'app/entities/photo-photo/photo-photo.module.ts',
                '//<--! sharedmodule -->',
                'imports: [' + baseNameUpperCase + 'SharedModule, RouterModule.forChild(photoRoute)],'
            );
        }
        if (this.buildTool == 'maven') {
            // Update Pom.xml dependencies
            this.addMavenDependency('net.coobird', 'thumbnailator', '0.4.8');
            this.addMavenDependency('com.drewnoakes', 'metadata-extractor', '2.13.0');
            this.addMavenDependency('org.apache.tika', 'tika-core', '1.23');
            this.addMavenDependency('org.apache.tika', 'tika-parsers', '1.23');
            this.addMavenDependency('net.sourceforge.tess4j', 'tess4j', '3.2.1');
        }
        if (this.buildTool == 'gradle') {
        }


        //
        // Files added in java package
        //

        // domain/Photo.java
        this.template('src/main/java/package/domain/Photo.java', javaDir + 'domain/Photo.java');
        this.rewriteFile(javaDir + 'domain/Photo.java', needle_package, 'package ' + this.packageName + '.domain;');

        // domain/PhotoLite.java
        this.template('src/main/java/package/domain/PhotoLite.java', javaDir + 'domain/PhotoLite.java');
        this.rewriteFile(javaDir + 'domain/PhotoLite.java', needle_package, 'package ' + this.packageName + '.domain;');

        // repository/PhotoRepository.java
        this.template('src/main/java/package/repository/PhotoRepository.java', javaDir + 'repository/PhotoRepository.java');
        this.rewriteFile(javaDir + 'repository/PhotoRepository.java', needle_package, 'package ' + this.packageName + '.repository;');
        this.rewriteFile(javaDir + 'repository/PhotoRepository.java', needle_import, 'import ' + this.packageName + '.domain.Photo;');

        // repository/PhotoLiteRepository.java
        this.template('src/main/java/package/repository/PhotoLiteRepository.java', javaDir + 'repository/PhotoLiteRepository.java');
        this.rewriteFile(javaDir + 'repository/PhotoLiteRepository.java', needle_package, 'package ' + this.packageName + '.repository;');
        this.rewriteFile(
            javaDir + 'repository/PhotoLiteRepository.java',
            needle_import,
            'import ' + this.packageName + '.domain.PhotoLite;'
        );

        // service/PhotoService.java
        this.template('src/main/java/package/service/PhotoService.java', javaDir + 'service/PhotoService.java');
        this.rewriteFile(javaDir + 'service/PhotoService.java', needle_package, 'package ' + this.packageName + '.service;');
        this.rewriteFile(javaDir + 'service/PhotoService.java', needle_import, 'import ' + this.packageName + '.service.dto.PhotoDTO;');

        // service/PhotoQueryService.java
        this.template('src/main/java/package/service/PhotoQueryService.java', javaDir + 'service/PhotoQueryService.java');
        this.rewriteFile(javaDir + 'service/PhotoQueryService.java', needle_package, 'package ' + this.packageName + '.service;');
        this.rewriteFile(javaDir + 'service/PhotoQueryService.java', needle_import, 'import ' + this.packageName + '.domain.*;');
        this.rewriteFile(
            javaDir + 'service/PhotoQueryService.java',
            needle_import,
            'import ' + this.packageName + '.repository.PhotoLiteRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/PhotoQueryService.java',
            needle_import,
            'import ' + this.packageName + '.repository.PhotoRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/PhotoQueryService.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.PhotoCriteria;'
        );
        this.rewriteFile(
            javaDir + 'service/PhotoQueryService.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.PhotoDTO;'
        );
        this.rewriteFile(
            javaDir + 'service/PhotoQueryService.java',
            needle_import,
            'import ' + this.packageName + '.service.mapper.PhotoLiteMapper;'
        );
        this.rewriteFile(
            javaDir + 'service/PhotoQueryService.java',
            needle_import,
            'import ' + this.packageName + '.service.mapper.PhotoMapper;'
        );

        // service/dto/PhotoDTO.java
        this.template('src/main/java/package/service/dto/PhotoDTO.java', javaDir + 'service/dto/PhotoDTO.java');
        this.rewriteFile(javaDir + 'service/dto/PhotoDTO.java', needle_package, 'package ' + this.packageName + '.service.dto;');

        // service/dto/PhotoCriteria.java
        this.template('src/main/java/package/service/dto/PhotoCriteria.java', javaDir + 'service/dto/PhotoCriteria.java');
        this.rewriteFile(javaDir + 'service/dto/PhotoCriteria.java', needle_package, 'package ' + this.packageName + '.service.dto;');

        // service/mapper/PhotoMapper.java
        this.template('src/main/java/package/service/mapper/PhotoMapper.java', javaDir + 'service/mapper/PhotoMapper.java');
        this.rewriteFile(javaDir + 'service/mapper/PhotoMapper.java', needle_package, 'package ' + this.packageName + '.service.mapper;');
        this.rewriteFile(javaDir + 'service/mapper/PhotoMapper.java', needle_import, 'import ' + this.packageName + '.domain.*;');
        this.rewriteFile(
            javaDir + 'service/mapper/PhotoMapper.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.PhotoDTO;'
        );

        // service/mapper/PhotoLiteMapper.java
        this.template('src/main/java/package/service/mapper/PhotoLiteMapper.java', javaDir + 'service/mapper/PhotoLiteMapper.java');
        this.rewriteFile(
            javaDir + 'service/mapper/PhotoLiteMapper.java',
            needle_package,
            'package ' + this.packageName + '.service.mapper;'
        );
        this.rewriteFile(javaDir + 'service/mapper/PhotoLiteMapper.java', needle_import, 'import ' + this.packageName + '.domain.*;');
        this.rewriteFile(
            javaDir + 'service/mapper/PhotoLiteMapper.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.PhotoDTO;'
        );

        // service/mapper/EntityMapper.java
        this.template('src/main/java/package/service/mapper/EntityMapper.java', javaDir + 'service/mapper/EntityMapper.java');
        this.rewriteFile(javaDir + 'service/mapper/EntityMapper.java', needle_package, 'package ' + this.packageName + '.service.mapper;');

        // web/rest/PhotoResource.java
        this.template('src/main/java/package/web/rest/PhotoResource.java', javaDir + 'web/rest/PhotoResource.java');
        this.rewriteFile(javaDir + 'web/rest/PhotoResource.java', needle_package, 'package ' + this.packageName + '.web.rest;');
        this.rewriteFile(
            javaDir + 'web/rest/PhotoResource.java',
            needle_import,
            'import ' + this.packageName + '.security.AuthoritiesConstants;'
        );
        this.rewriteFile(javaDir + 'web/rest/PhotoResource.java', needle_import, 'import ' + this.packageName + '.security.SecurityUtils;');
        this.rewriteFile(
            javaDir + 'web/rest/PhotoResource.java',
            needle_import,
            'import ' + this.packageName + '.service.PhotoQueryService;'
        );
        this.rewriteFile(javaDir + 'web/rest/PhotoResource.java', needle_import, 'import ' + this.packageName + '.service.PhotoService;');
        this.rewriteFile(
            javaDir + 'web/rest/PhotoResource.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.PhotoCriteria;'
        );
        this.rewriteFile(javaDir + 'web/rest/PhotoResource.java', needle_import, 'import ' + this.packageName + '.service.dto.PhotoDTO;');
        this.rewriteFile(javaDir + 'web/rest/PhotoResource.java', needle_import, 'import ' + this.packageName + '.service.util.MimeTypes;');
        this.rewriteFile(
            javaDir + 'web/rest/PhotoResource.java',
            needle_import,
            'import ' + this.packageName + '.web.rest.errors.BadRequestAlertException;'
        );

        //
        // Files indexation/
        //

        // indexation/tampon.png
        //this.template('src/main/java/package/indexation/tampon.png', javaDir + 'indexation/tampon.png');

        // indexation/Indexation.java
        this.template('src/main/java/package/indexation/Indexation.java', javaDir + 'indexation/Indexation.java');
        this.rewriteFile(javaDir + 'indexation/Indexation.java', needle_package, 'package ' + this.packageName + '.indexation;');
        this.rewriteFile(
            javaDir + 'indexation/Indexation.java',
            '//<--! JAVAPACKAGE -->',
            'public static final String JAVAPACKAGE = "' + javaDir + '";'
        );

        // indexation/imageAI/output.jpg
        //this.template('src/main/java/package/indexation/imageAI/output.jpg', javaDir + 'indexation/imageAI/output.png');

        // indexation/imageAI/imageAI.py
        this.template('src/main/java/package/indexation/imageAI/imageAI.py', javaDir + 'indexation/imageAI/imageAI.py');

        // // indexation/tessdata/eng.traineddata
        // this.fs.copy(
        //     this.templatePath('src/main/java/package/indexation/tessdata/eng.traineddata'),
        //     this.destinationPath(javaDir + 'indexation/tessdata/eng.traineddata')
        // );
        //
        // // indexation/tessdata/osd.traineddata
        // this.fs.copy(
        //     this.templatePath('src/main/java/package/indexation/tessdata/osd.traineddata'),
        //     this.destinationPath(javaDir + 'indexation/tessdata/osd.traineddata')
        // );
        //
        // // indexation/tessdata/pdf.ttf
        // this.template('src/main/java/package/indexation/tessdata/pdf.ttf', javaDir + 'indexation/tessdata/pdf.ttf');
        //
        // // indexation/configs
        // this.fs.copy(
        //     this.templatePath('src/main/java/package/indexation/tessdata/configs'),
        //     this.destinationPath(javaDir + 'indexation/tessdata/configs')
        // );
        //
        // // indexation/tessconfigs
        // this.fs.copy(
        //     this.templatePath('src/main/java/package/indexation/tessdata/tessconfigs'),
        //     this.destinationPath(javaDir + 'indexation/tessdata/tessconfigs')
        // );
        //
        // // indexation/tessconfigs
        // this.fs.copy(
        //     this.templatePath('src/main/java/package/indexation/tessdata/tessconfigs'),
        //     this.destinationPath(javaDir + 'indexation/tessdata/tessconfigs')
        // );

        // // indexation/imageAI/resnet50_coco_best_v2.0.1.h5
        // this.fs.copy(
        //   this.templatePath('src/main/java/package/indexation/imageAI/resnet50_coco_best_v2.0.1.h5'),
        //   this.destinationPath(javaDir + 'indexation/imageAI/resnet50_coco_best_v2.0.1.h5')
        // );
        //
        // // indexation/imageAI/resnet50_weights_tf_dim_ordering_tf_kernels.h5
        // this.fs.copy(
        //   this.templatePath('src/main/java/package/indexation/imageAI/resnet50_weights_tf_dim_ordering_tf_kernels.h5'),
        //   this.destinationPath(javaDir + 'indexation/imageAI/resnet50_weights_tf_dim_ordering_tf_kernels.h5')
        // );

        //
        // Files added in resources package
        //
        this.template(
            'src/main/resources/config/liquibase/fake-data/blob/hipster.png',
            resourceDir + 'config/liquibase/fake-data/blob/hipster.png'
        );
        this.template(
            'src/main/resources/config/liquibase/fake-data/blob/hipster.txt',
            resourceDir + 'config/liquibase/fake-data/blob/hipster.txt'
        );
        this.template('src/main/resources/config/liquibase/fake-data/photo.csv', resourceDir + 'config/liquibase/fake-data/photo.csv');
        this.template(
            'src/main/resources/config/liquibase/changelog/20200201065301_added_entity_constraints_Photo.xml',
            resourceDir + 'config/liquibase/changelog/20200201065301_added_entity_constraints_Photo.xml'
        );
        this.template(
            'src/main/resources/config/liquibase/changelog/20200201065301_added_entity_Photo.xml',
            resourceDir + 'config/liquibase/changelog/20200201065301_added_entity_Photo.xml'
        );

        //
        // service/util
        //

        // service/util/MetadataUtil.java
        this.template('src/main/java/package/service/util/MetadataUtil.java', javaDir + 'service/util/MetadataUtil.java');
        this.rewriteFile(javaDir + 'service/util/MetadataUtil.java', needle_package, 'package ' + this.packageName + '.service.util;');

        // service/util/MimeTypes.java
        this.template('src/main/java/package/service/util/MimeTypes.java', javaDir + 'service/util/MimeTypes.java');
        this.rewriteFile(javaDir + 'service/util/MimeTypes.java', needle_package, 'package ' + this.packageName + '.service.util;');

        // service/util/RandomUtil.java
        this.template('src/main/java/package/service/util/RandomUtil.java', javaDir + 'service/util/RandomUtil.java');
        this.rewriteFile(javaDir + 'service/util/RandomUtil.java', needle_package, 'package ' + this.packageName + '.service.util;');

        // service/util/SHAUtil.java
        this.template('src/main/java/package/service/util/SHAUtil.java', javaDir + 'service/util/SHAUtil.java');
        this.rewriteFile(javaDir + 'service/util/SHAUtil.java', needle_package, 'package ' + this.packageName + '.service.util;');

        // service/util/ThumbnailUtil.java
        this.template('src/main/java/package/service/util/ThumbnailUtil.java', javaDir + 'service/util/ThumbnailUtil.java');
        this.rewriteFile(javaDir + 'service/util/ThumbnailUtil.java', needle_package, 'package ' + this.packageName + '.service.util;');

        //
        // service/impl
        //

        // service/impl/PhotoServiceImpl
        this.template('src/main/java/package/service/impl/PhotoServiceImpl.java', javaDir + 'service/impl/PhotoServiceImpl.java');
        this.rewriteFile(javaDir + 'service/impl/PhotoServiceImpl.java', needle_package, 'package ' + this.packageName + '.service.impl;');
        this.rewriteFile(javaDir + 'service/impl/PhotoServiceImpl.java', needle_import, 'import ' + this.packageName + '.domain.Photo;');
        this.rewriteFile(
            javaDir + 'service/impl/PhotoServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.indexation.Indexation;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/PhotoServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.repository.PhotoLiteRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/PhotoServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.repository.PhotoRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/PhotoServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.service.PhotoService;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/PhotoServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.service.dto.PhotoDTO;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/PhotoServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.service.mapper.PhotoLiteMapper;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/PhotoServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.service.mapper.PhotoMapper;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/PhotoServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.service.util.MetadataUtil;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/PhotoServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.service.util.MimeTypes;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/PhotoServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.service.util.SHAUtil;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/PhotoServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.service.util.ThumbnailUtil;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/PhotoServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.security.SecurityUtils;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/PhotoServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.repository.UserRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/PhotoServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.repository.search.PhotoLiteSearchRepository;'
        );
        this.rewriteFile(
            javaDir + 'service/impl/PhotoServiceImpl.java',
            needle_import,
            'import ' + this.packageName + '.domain.PhotoLite;'
        );

        // Changelog added
        this.addLiquibaseChangelogToMaster(
            '20200201065301_added_entity_Photo',
            '<!-- jhipster-needle-liquibase-add-changelog - JHipster will add liquibase changelogs here -->'
        );
        this.addLiquibaseChangelogToMaster(
            '20200201065301_added_entity_constraints_Photo',
            '<!-- jhipster-needle-liquibase-add-constraints-changelog - JHipster will add liquibase constraints changelogs here -->'
        );

        // Entry added to cache
        // TODO: remove the hardcoded arg (com/jhipster/app)
        //this.addEntryToEhcache( this.packageName + '.domain.Photo.class.getName()', 'package/path/test');

        //
        // application.yml
        //
        this.rewriteFile(
            resourceDir + 'config/application.yml',
            '# application:',
            'thumbnail:\n  x1:\n    maxDim: 300\n  x2:\n    maxDim: 600\n\nlazyblob:\n  cacheControl:\n    maxAge: 60'
        );

        //
        // Update .gitignore with downloads files
        //

        this.rewriteFile(
            '.gitignore',
            '',
            '\n######################\n# PhotoModule \n######################\n' + javaDir + 'indexation/tessdata/eng.traineddata'
        );

        this.rewriteFile('.gitignore', '', javaDir + 'indexation/imageAI/resnet50_weights_tf_dim_ordering_tf_kernels.h5');

        this.rewriteFile('.gitignore', '', javaDir + 'indexation/imageAI/resnet50_coco_best_v2.0.1.h5');

        //
        // Elasticsearch
        //

        // repository/search/package-info.java
        this.template('src/main/java/package/repository/search/package-info.java', javaDir + 'repository/search/package-info.java');
        this.rewriteFile(javaDir + 'repository/search/package-info.java', needle_package, 'package ' + this.packageName + '.repository.search;');

        // repository/search/PhotoLiteSearchRepository.java
        this.template('src/main/java/package/repository/search/PhotoLiteSearchRepository.java', javaDir + 'repository/search/PhotoLiteSearchRepository.java');
        this.rewriteFile(javaDir + 'repository/search/PhotoLiteSearchRepository.java', needle_package, 'package ' + this.packageName + '.repository.search;');
        this.rewriteFile(javaDir + 'repository/search/PhotoLiteSearchRepository.java', needle_import, 'import ' + this.packageName + '.domain.Photo;');
        this.rewriteFile(javaDir + 'repository/search/PhotoLiteSearchRepository.java', needle_import, 'import ' + this.packageName + '.domain.PhotoLite;');

        // repository/search/UserSearchRepository.java
        this.template('src/main/java/package/repository/search/UserSearchRepository.java', javaDir + 'repository/search/UserSearchRepository.java');
        this.rewriteFile(javaDir + 'repository/search/UserSearchRepository.java', needle_package, 'package ' + this.packageName + '.repository.search;');
        this.rewriteFile(javaDir + 'repository/search/UserSearchRepository.java', needle_import, 'import ' + this.packageName + '.domain.User;');

        //
        // Tessdata + neuronal network
        //

        // Delete Files
        execute('rm ' + javaDir + 'indexation/tessdata/eng.traineddata');
        execute('rm ' + javaDir + 'indexation/imageAI/resnet50_weights_tf_dim_ordering_tf_kernels.h5');
        execute('rm ' + javaDir + 'indexation/imageAI/resnet50_coco_best_v2.0.1.h5');

        // Download Files
        execute(
            'wget -P ' + javaDir + 'indexation/tessdata https://raw.githubusercontent.com/tesseract-ocr/tessdata/master/eng.traineddata'
        );
        execute(
            'wget -P ' +
                javaDir +
                'indexation/imageAI https://github.com/OlafenwaMoses/ImageAI/releases/download/1.0/resnet50_weights_tf_dim_ordering_tf_kernels.h5'
        );
        execute(
            'wget -P ' +
                javaDir +
                'indexation/imageAI https://github.com/OlafenwaMoses/ImageAI/releases/download/1.0/resnet50_coco_best_v2.0.1.h5'
        );

    }

    install() {
        const logMsg = `To install your dependencies manually, run: ${chalk.yellow.bold(`${this.clientPackageManager} install`)}`;

        const injectDependenciesAndConstants = err => {
            if (err) {
                this.warning('Install of dependencies failed!');
                this.log(logMsg);
            }
        };
        const installConfig = {
            bower: false,
            npm: this.clientPackageManager !== 'yarn',
            yarn: this.clientPackageManager === 'yarn',
            callback: injectDependenciesAndConstants
        };
        if (this.options['skip-install']) {
            this.log(logMsg);
        } else {
            this.installDependencies(installConfig);
        }
    }

    end() {
        this.info('Download Tesseract data, Neural Network...');
    }
};

//Attaching our method to the String Object
String.prototype.cleanup = function() {
    const re = /([^a-zA-Z0-9][a-zA-Z])/g;
    var tamp = this.replace(re, function(x) {
        return x.toUpperCase();
    }).replace(/[^a-zA-Z0-9]/g, '');
    return tamp.charAt(0).toUpperCase() + tamp.slice(1);
};

function execute(command) {
    const exec = require('child_process').exec;

    exec(command, (err, stdout, stderr) => {
        process.stdout.write(stdout);
    });
}

function isElasticsearch() {
    const data = fs.readFileSync('./pom.xml', 'utf8');
    return data.includes('elasticsearch');
}
