# Imagen ligera de Apache HTTP Server
FROM httpd:2.4-alpine

# Limpia el contenido por defecto y copia el sitio estático
RUN rm -rf /usr/local/apache2/htdocs/*
COPY ./ /usr/local/apache2/htdocs/
# Apache escucha en el puerto 80 por defecto
EXPOSE 80

# Mantiene httpd en primer plano
CMD ["httpd-foreground"]
