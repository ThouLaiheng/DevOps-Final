FROM eclipse-temurin:23-jdk

RUN apt-get update && apt-get install -y nginx openssh-server php-cli && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

RUN mkdir /var/run/sshd && \
    echo 'root:Hello@123' | chpasswd && \
    sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config && \
    sed -i 's/#PasswordAuthentication yes/PasswordAuthentication yes/' /etc/ssh/sshd_config

COPY target/field-rental-1.0.0.jar /app/app.jar

COPY nginx.conf /etc/nginx/sites-available/default
RUN ln -sf /etc/nginx/sites-available/default /etc/nginx/sites-enabled/

COPY start.sh /app/start.sh
RUN chmod +x /app/start.sh

EXPOSE 80 8080 22

CMD ["/app/start.sh"]
