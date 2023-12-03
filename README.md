cd C:\Users\austi\Documents\Chapman\se300\final\se300-final 

mvn clean
mvn install

mvn spring-boot:run


mvn clean verify sonar:sonar -Dsonar.projectKey=se300Final -Dsonar.projectName='se300Final' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_af54985a082b8ac37e98a4bfa9c00c0d45bb565c

echo "done"

