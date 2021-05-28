# Tempfile Module
import tempfile

# Create a temporary file
# can probably use 'with' here to not have to worry about
# closing the file at the end
tempFile = tempfile.TemporaryFile()

# Write to a temporary file
tempFile.write(b"Save this special number for me: 5678309")
tempFile.seek(0)

# Read the temporary file
print(tempFile.read())

# Close the temporary file
tempFile.close()
