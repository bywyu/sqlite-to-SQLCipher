sqlite-to-SQLCipher
===================

A small Android application that converts a .sqlite to an encrypted SQLCipher database using a password. These encrypted databases will be ready to include in another project's APK.

Instructions
-------
1. Download the Eclipse project from GitHub
2. Open the project with Eclipse
3. Put your file containing your SQLite database creating statements in the `Assets` folder
4. Change the `SQLITE_FILE`, `DB_NAME`, and `DB_PASSWORD` contants in the SQLite2SQLCipher.java file
5. Run the application on the Emulator (This makes it easy to grab the file off it from the DDMS File Explorer)
6. Once the application finishes running, use the DDMS File Explorer to navigate to the `/data/data/com.drspaceboo.sqlite2sqlcipher/databases` folder and download your .db file
7. Use your encrypted database in your app :D

License
-------
This program is released as is without warrenty under an [Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0). Feel free to use this how every you want, take the code! Just send some good vibes my way.

[SLQCipher has it's own license](http://sqlcipher.net/license), please read up on that before including any of my code or theirs in your project. I take no responsibility for any of their code or properties and do not claim to own them.

Contact details
-------
Author: Dr Boo

Website: [http://www.drspaceboo.com](http://www.drspaceboo.com/)

Email: contact [at] drspaceboo [dot] com

Please let me know about any improvements I can make to this. I am more then happy to incorporate commits if it means improving the application or user experience of this little tool.