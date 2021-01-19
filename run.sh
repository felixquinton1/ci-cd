#!/bin/bash

url="https://www.youtube.com/watch?v=xA8-6X8aR3o?autoplay=1"
saying="Vous avez 10h de travail maximum : quand la musique s'arrête, vous devriez pouvoir vous arrêter..."

echo "${saying}"
{ command -v say >/dev/null 2>&1 && say "${saying}"; } || :

{ command -v open >/dev/null 2>&1 && open "${url}"; } || { command -v xdg-open >/dev/null 2>&1 && xdg-open "${url}";} || echo "Please open ${url} and enjoy!"

exit 0
