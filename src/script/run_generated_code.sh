javac output/GeneratedCode.java

if [ $? -eq 0 ]; then
    java -classpath output GeneratedCode > output/output.txt

else
    echo "Compilation failed"
fi