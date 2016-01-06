echo OpenPKW Weryfikator - Initial Database Load

echo Removing files from previous uploads
rm /home/rancher/openpkw-weryfikator/database_initial_load/openpkw-utils-csv.zip
sudo docker exec openpkw-weryfikator rm -Rf /root/initial_database_load

echo Downloading uploader package
wget http://www.sebastian-celejewski.pl/openpkw/openpkw-utils-csv.zip

echo Creating temporary directory inside the Docker container
sudo docker exec openpkw-weryfikator mkdir /root/initial_database_load

echo Copying uploader package into the Docker container
sudo docker cp openpkw-utils-csv.zip openpkw-weryfikator:/root/initial_database_load/openpkw-utils-csv.zip

echo Extracting uploader package inside the Docker container
sudo docker exec openpkw-weryfikator unzip /root/initial_database_load/openpkw-utils-csv.zip -d /root/initial_database_load

echo Launching uploader
sudo docker exec openpkw-weryfikator java -jar /root/initial_database_load/openpkw-utils-csv-0.1.0-SNAPSHOT.jar

echo Done