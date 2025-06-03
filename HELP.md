# Update package list
sudo apt update

# Install Git
sudo apt install git -y

# Install Docker
sudo apt install docker.io -y
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker $USER  # Log out and back in after this

# Install Docker Compose
sudo apt install docker-compose-plugin -y

# Install Maven
sudo apt install maven -y

# Install Java 21
sudo apt install openjdk-21-jdk -y

# Verify installations
git --version
docker --version
docker compose version
mvn -v
java -version

# Start Docker Services:

- docker compose up -d --build (Builds and starts all services in the background).
The price-service is built from the Dockerfile in the project root.

- To access the application go to: localhost:9081
- To access the h2 database console go to: localhost:9081/h2-console
