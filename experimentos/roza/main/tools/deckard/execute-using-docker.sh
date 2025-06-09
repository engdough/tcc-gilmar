rozaPath=`realpath ../../../`

docker run -v $rozaPath:/roza deckard bash -c "cd main/tools/deckard && ls -l ../../exec && umask 000 && bash tool/scripts/clonedetect/deckard.sh"
