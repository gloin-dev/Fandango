# Upload a file and run the benchmark
baseURL=http://localhost:8585/api/images

imageID=$(curl --request POST \
  --url $baseURL \
  --header 'Content-Type: multipart/form-data' \
  --form file=@unsplash.jpg | jq --raw-output '.id')

ab -g output.tsv -c 100 -n 2000 "$baseURL"/"$imageID"

gnuplot plotconfig.p
