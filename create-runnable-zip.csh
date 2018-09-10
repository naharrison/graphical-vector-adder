#!/bin/csh -f

rm -rf vecadd-app vecadd-app.zip

mvn install
mvn clean compile assembly:single
mkdir vecadd-app
cp target/vecadd-1.0-SNAPSHOT-jar-with-dependencies.jar vecadd-app
chmod +x vecadd-app/vecadd-1.0-SNAPSHOT-jar-with-dependencies.jar
mv vecadd-app/vecadd-1.0-SNAPSHOT-jar-with-dependencies.jar vecadd-app/vecadd.jar
cp -r data vecadd-app
zip -r vecadd-app.zip vecadd-app
rm -rf vecadd-app

echo "unzip vecadd-app.zip ; cd vecadd-app ; java -jar vecadd.jar"
