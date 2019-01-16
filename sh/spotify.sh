#!/bin/bash
while IFS='' read -r line || [[ -n "$line" ]]; do
    curl -s $line > test.txt
    #price
    # printf "%d;" $(shuf -i 30-50 -n 1)
    #album name
    cat test.txt | sed -n 's:.*Featured on \(.*\)<\/a.*:\1:p' | sed 1q | cut -d ">" -f 2 | tr '\n' ';'| sed -e 's/\&\#039\;/'\''/g' | sed -e 's/amp\;//g' | sed -e 's/\&quot\;/\"/g' | sed -r 's/;/#/g'
    #print date
    cat test.txt | sed -n 's:.*datePublished\(.*\)potentialAction.*:\1:p' | cut -d "\"" -f 3 | cut -d "-" -f 1 | tr '\n' ' ' | sed -e 's/\&\#039\;/'\''/g' | sed -e 's/amp\;//g' | sed -e 's/\&quot\;/\"/g' | sed 's/.$//'
    #print url 
    curl -s 'https://open.spotify.com/album/'$(cat test.txt | sed -n 's:.*com\/album\/\(.*\)meta.*:\1:p' | cut -d "\"" -f 1) | grep -oP '(?<=Spotify.Entity =).*(?<=};)' | jq .images[1].url | sed -e 's/\"/#/g'
    #artist
    cat test.txt | sed -n 's:.*a song by \(.*\) on Spotify.*:\1:p' | sed 1q | tr '\n' '#' | sed -e 's/\&\#039\;/'\''/g' | sed -e 's/amp\;//g' | sed -e 's/\&quot\;/\"/g'
    #print info about license
    #cat test.txt | sed -n 's:.*&#8471\; \(.*\)*:\1:p' | tr '\n' ' '
    #genre
    genre=$(curl -s 'https://open.spotify.com/artist/'$(cat test.txt | sed -n 's:.*music\:musician\" content\=\(.*\)music\:release_date.*:\1:p' |  sed -e 's/\/.*\///g' | cut -d ':' -f 2 | cut -d '"' -f 1) | sed -n 's:.*genres\(.*\)images.*:\1:p' | cut -d '"' -f 3 | sed 's/href//g')
    genre=${genre//$'\n'/}
    printf "%s#" "$genre"
     #songs count and tracklist
    sc=$(curl -s 'https://open.spotify.com/album/'$(cat test.txt | sed -n 's:.*com\/album\/\(.*\)meta.*:\1:p' | cut -d "\"" -f 1) | grep -oP '(?<=Spotify.Entity =).*(?<=};)' | jq -j '.total_tracks' | sed -e 's/\/.*\///g')
    sc=${sc//$'\n'/}
    printf "%s#" "$sc"
    counter=0
    length=0;
    curl -s 'https://open.spotify.com/album/'$(cat test.txt | sed -n 's:.*com\/album\/\(.*\)meta.*:\1:p' | cut -d "\"" -f 1) | grep -oP '(?<=Spotify.Entity =).*(?<=};)' > test2.txt
    while [[ $counter -ne $sc ]];
    do
    	str=".tracks.items[$counter].name"
    	str2=".tracks.items[$counter].duration_ms"
    	value=$(cat test2.txt | jq -r $str )
    	value2=$(cat test2.txt | jq -r $str2)
    	let "counter2 = $counter + 1"

    	let "length = $length + $value2"
    	printf "%s" "${counter2}. ${value}*"
    	((counter++))
    done;
    printf "#%s" "$length"
    printf "\n"
done < "$1";
rm test.txt
