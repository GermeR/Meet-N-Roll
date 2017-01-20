for i in $(./ultraLs -fc src)
do
	javac -sourcepath src -cp ../../lib/servlet-api.jar:lib/postgresql-9.3-1102.jdbc41.jar -d WEB-INF/classes/ $i
done
