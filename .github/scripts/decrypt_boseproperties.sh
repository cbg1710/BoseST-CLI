#!/bin/sh

# Decrypt the file
# --batch to prevent interactive command
# --yes to assume "yes" for questions
gpg --quiet --batch --yes --decrypt --passphrase="$BOSE_PROPERTIES_KEY" \
--output bose.properties bose.properties.gpg