# Zipfile Module
import zipfile

zip_file_path = "python_3_standard_library\\Ch03\\03_07\\Archive.zip"

# Open and List
# can probably use 'with' here to not have to worry about
# closing the file at the end
zip = zipfile.ZipFile(zip_file_path, "r")
print(zip.namelist())

# Metadata in the zip folder
for meta in zip.infolist():
    print(meta)

info = zip.getinfo("purchased.txt")
print(info)

# Access to files in zip folder
print(zip.read("wishlist.txt"))
with zip.open("wishlist.txt") as f:
    print(f.read())

# Extracting files
# zip.extract("purchased.txt")
zip.extractall()

# Closing the zip
zip.close()
