
PEM_PATH="/home/okejka/Documents/clinic-management-backend-key.pem"
EC2_URL="ec2-user@54.196.217.57"

ssh -i $PEM_PATH $EC2_URL "sudo kill \$(sudo lsof -ti:443)"
mvn clean install -DskipTests

scp -i $PEM_PATH ./target/clinic_system-0.0.1-SNAPSHOT.jar $EC2_URL:~/application.jar

ssh -i $PEM_PATH $EC2_URL "sudo ./run.sh"

