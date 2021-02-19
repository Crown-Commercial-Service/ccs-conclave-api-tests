#!/bin/bash
echo "Starting deploying test reports to Github Pages...."
( cd target/report
# git init
# git config user.name $GITNAME
# git config user.email $GITEMAIL
 git add .
 git commit -m "Latest Test Reports deployed to Github Pages"
 git push "https://$GITHUBTOKEN@$GHREF" gh-pages
# git push --force --quiet "https://$GITHUBTOKEN@$GHREF" master:gh-pages > /dev/null 2>&1
)