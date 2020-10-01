#!/bin/sh

# Decrypt the file
mkdir $HOME/secrets
# --batch to prevent interactive command
# --yes to assume "yes" for questions
gpg --quiet --batch --yes --decrypt --passphrase="$BOSE_PROPERTIES_KEY" \
--output $HOME/secrets/bose.properties bose.properties.gpg