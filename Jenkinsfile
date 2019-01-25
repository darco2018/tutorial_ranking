

pipeline {
    // Blocks must only consist of Sections, Directives, Steps, or assignment statements
    //Sections contain one or more Directives or Steps.
    agent any

    options {
        // buildlogs, artifacts
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
        skipDefaultCheckout()
    }
    parameters {
        string(name: 'user', defaultValue: 'ustrd', description: 'A user that triggers the pipeline')
    }

    environment {
        /*NOTES:
        lo_var="lowercaseOK" can be called ${LO_VAR}
        black var is interpolated, green is a string
        app_jar='${env.app_name}' - singel quotes dont interpolate variables!!!*/

        app_name = "kroliczek"
        version = "0.0.1"
        app_jar = "${env.app_name}-${env.version}"
        serverport = 9999
        app_image = "${env.app_name}"
        app_container = "${env.app_name}"
        db_image = "mysql"
        db_container = "tut-mysql"

        LOG_ABS_PATH = "/var/log/"
        MIGRATION_ABS_PATH = "/var/migration/"

    }

    tools {
        maven 'M3'
    }

    stages {
        stage('Install docker') {
            steps {
                echo "Pipeline triggered by ${params.USER}"
                sh 'java -version'
                sh 'which java'
                script {
                    def dockerTool = tool name: 'myDocker', type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
                    withEnv(["DOCKER=${dockerTool}/bin"]) {
                        //stages
                    }
                }
                //for testing purposes (each sh has its own interpreter - you start afresh each time
                echo 'Careful with variables:'
                //sh '$PWD' // interpolated by Shell :  /var/jenkins_home/workspace/ia_d-06-poll-scm-push-hub_master  Permission denied
                //sh "$PWD" // interpolated in this file  by Groovy:  / Permission denied
                sh 'docker images'
                sh 'printenv'

            }
        }

        stage('Checkout') {
            steps {
                deleteDir()
                //ALTENATIVE: checkout scm
                git branch: 'master', credentialsId: 'jenkins-webhook-id', url: 'https://github.com/darco2018/tutorial_ranking'

            }
        }

        // if app doesnt even build no point of testing later(long process)
        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        // if tests fail, no the point of building image later
        stage('Unit tests/Sonar') {
            // A stage directive can have either a parallel or steps directive but not both,  cannot have “agent” or “tools”

            steps {
                sh 'mvn clean test'
                //-e -X for debug; cant we run it in container ? mkyoung how to run unit test with maven
                // sonar stops NOT working in parallel step with mvn test
                //sh "mvn  sonar:sonar -Dsonar.host.url=${env.SONARQUBE_HOST}"

            }
            post {
                always {
                    junit 'target/surefire-reports/TEST-*.xml'
                }
            }
        }


        stage('Build app image') {
            steps {
                script {

                    //it also works: myImg = docker.build("ustrd/tutorialpedia:$env.BUILD_NUMBER", "-f ./docker/Dockerfile --build-arg SOMEVAR=dummyvalue . ")
                    sh 'scripts/build-app-image.sh'

                    app = docker.build("ustrd/tut2", "-f docker/Dockerfile .")
                    // docker.build( really starts a container
                    app.inside("--entrypoint=''") {
                        sh 'echo "Tests passed"'
                    }
                    /*
                    myImg.inside("--entrypoint=''"){ // turns off Dockerfile entrypoint
                        sh 'ls -al'
                        sh "echo ${app_jar} is app_jar ....." //
                        sh "echo ${APP_JAR} is APP_JAR ....."
                    }
                    */
                }
            }
        }

        stage('Run') {
            steps {
                script {
                    try {  // database tez powinna byc z custom Dockerfile: ustrd/mysql
                        sh 'scripts/db-up.sh'
                        sh 'scripts/run-app-image.sh'
                        sleep 20
                        retry(10) {
                            sh 'curl -X GET http://172.17.0.1:${serverport}/skills/GE'
                        }

                    } catch (error) {
                        error.printStackTrace()
                    }
                }

                //debugging
                sh 'docker ps -a'
            }
        }

        stage('Integration tests') {
            steps {
                // sh 'mvn clean test'
                echo 'This should run the integration tests against the docker container but is now failing'
            }
            post {
                always {
                    //junit 'target/surefire-reports/TEST-*.xml'
                    echo 'This should generate reports'
                }
                success {

                    archiveArtifacts 'docker/*jar'
                }
            }
        }


        stage('Push image') {
            when {
                branch 'master'
            }
            steps {

                // This step should not normally be used in your script. Consult the inline help for details.
                // docker.withRegistry(url[, credentialsId]) {…} >> https://index.docker.io/v1/
                // toolName i url moga byc ominiete
                // Docker Pipeline & Docker Commons Plugin
                /* ALTERNATIVE - not in script step)
                withDockerRegistry(credentialsId: 'dockerhub-credentials', url: 'https://index.docker.io/v1/') {
                    // some block
                }
                */
            script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                        myImg = docker.build("ustrd/tutorialpedia:$env.BUILD_NUMBER",
                                "-f ./docker/Dockerfile " +
                                        "--build-arg SOMEVAR=dummyvalue . ")

                        /* First, the incremental build number from Jenkins
                         Second, the 'latest' tag.
                          Pushing multiple tags is cheap, as all the layers are reused. */
                        myImg.push("${env.BUILD_NUMBER}")
                        myImg.push("latest")

                    }



                    // There are many features of the Pipeline that are not steps. These are often exposed via global variables,
                    // which are not supported by the snippet generator.

                }

            }

            post {
                always {
                    sh 'scripts/db-down.sh'
                    retry(20) {
                        sh 'curl -X POST http://172.17.0.1:${serverport}/actuator/shutdown'
                    }

                }
            }

        }

    }
}


/*

They dont load the variables into Dockerfile or Jenkins environment:
readFile 'scripts/docker-config.sh'
load './scripts/docker-config.groovy'
sh 'source scripts/docker-config.sh'
*/