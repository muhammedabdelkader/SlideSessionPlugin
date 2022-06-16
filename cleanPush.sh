echo -ne "Prepare Push"
rm -rf out/
rm -rf *.jar 
git add . 
git commit -m "commit date: "+date
git push 
echo -ne "Finished " 
