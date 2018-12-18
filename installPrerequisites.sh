#!/bin/sh

echo "
************************************************
************** Prerequisites *******************
************************************************
***                                          ***
***   1. Java 8 (or higher)                  ***
***   2. Python + Pip + Flask                ***
***   3. Erlang + Elixir + Hex + Phoenix     ***
***                                          ***
***   If you don't need to compile the apps  ***
***   you can just run the executables for   ***
***         your specific platform           ***
***   (see runBinaries.sh and runExecs.bat)  ***
***                                          ***
***     If you continue with this script,    ***
***     please be sure you have Java 8+,     ***
***   Erlang, Elixir and Python installed    ***
***     on your system and path-enabled      ***
************************************************
"

read -p "Do you want to install the prerequisites? (Linux and OSX only) - y/n " -n 1 -r
echo 
if [[ $REPLY =~ ^[Yy]$ ]]
then
    if type -p java; then
        echo found java executable in PATH
        _java=java
    elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
        echo found java executable in JAVA_HOME
        _java="$JAVA_HOME/bin/java"
    else
        echo "no java"
    fi

    if [[ "$_java" ]]; then
        version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
        echo version "$version"
        if [[ "$version" > "1.8" ]]; then
            echo version is more than 1.8
        else
            echo version is less than 1.8
        fi
    fi

    # Python prerequisites
    easy_install pip                      # Install Pip
    pip install Flask                     # Install Flask
    
    # Erlang prerequisites
    mix local.hex                         # Install Hex
    mix archive.install hex phx_new 1.4.0 # Install Phoenix    
fi