@echo off
setlocal

REM Set the installation directory
set INSTALL_DIR=C:\Program Files\PoemManager

REM Set MySQL connection details
set DB_HOST=localhost
set DB_PORT=3306
set DB_USER=root
set DB_PASSWORD=
set DB_NAME=scdproject

REM Check if Java is installed and the version is 1.8 or above
java -version 2>&1 | findstr /I "version" | findstr /V /I "1.7" >nul
if %errorlevel% neq 0 (
    echo Error: Java 1.8 or above is required to run this application.
    goto :EOF
)

REM Check if MySQL is installed
where mysql >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: MySQL is not installed. Please install MySQL before running the application.
    goto :EOF
)

REM Create installation directory
if not exist "%INSTALL_DIR%" mkdir "%INSTALL_DIR%"

REM Copy the main executable JAR file
copy /Y PoemManager.jar "%INSTALL_DIR%"

REM Copy external JAR files to the 'jar' folder
copy /Y jar\* "%INSTALL_DIR%\jar"

REM Create logs directory
if not exist "%INSTALL_DIR%\logs" mkdir "%INSTALL_DIR%\logs"

set DB_MYSQL_EXECUTABLE=C:\xampp\mysql\bin\mysql.exe

where %DB_MYSQL_EXECUTABLE% >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: MySQL is not installed. Please install MySQL before running the application.
    goto :EOF
)

REM Create MySQL database
%DB_MYSQL_EXECUTABLE% -h%DB_HOST% -P%DB_PORT% -u%DB_USER% -p%DB_PASSWORD% -e "CREATE DATABASE IF NOT EXISTS %DB_NAME%;"

REM Execute the SQL script to create tables and populate initial data
mysql -h%DB_HOST% -P%DB_PORT% -u%DB_USER% -p%DB_PASSWORD% %DB_NAME% < db\database_structure.sql

REM Copy configuration files
copy /Y config\application.properties "%INSTALL_DIR%\config"
copy /Y config\log4j2.properties "%INSTALL_DIR%\config"

echo Installation completed successfully.
echo Please run the application using: java -jar "%INSTALL_DIR%\PoemManager.jar"

endlocal
