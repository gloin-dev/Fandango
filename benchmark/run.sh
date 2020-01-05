# Upload a file and run the benchmark ( be sure Fandango is running )
baseURL=http://localhost:8585/api/images

imageID=$(curl --request POST \
  --url $baseURL \
  --header 'Content-Type: multipart/form-data' \
  --form file=@fox.jpg | jq --raw-output '.id')

ab -g output.tsv -c 1000 -n 20000 "$baseURL"/"$imageID"

gnuplot plotconfig.p
