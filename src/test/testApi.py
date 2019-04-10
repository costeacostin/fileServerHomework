import requests
import sys
import random
import os

#This test check if the fileserver is working
#TODO: Check files and body content. Run multiple requests in separate threads in order to see concurency behaviour
if len(sys.argv) == 4:
    print ('Python API tester for Java fileServer')
    print sys.argv
    serverIp = sys.argv[1]
    serverPort = sys.argv[2]
    serverHome = sys.argv[3]
    hostUrl = 'http://' + serverIp + ':' + serverPort

    createdFiles = []

    #STEP 1 Test GET method on a file that not exist
    randomFile = 'dir1/dir2/file' + str(random.randint(1,100000)) + '.txt'
    fullPath = serverHome + "/" + randomFile
    print 'STEP 1 Test GET method on a file that not exist'
    resp = requests.get(hostUrl + '/' + randomFile)
    if resp.status_code == 404:
        print 'DONE <SUCCESS>'
    else:
        print 'DONE <FAIL>'

    #STEP 2 Test POST method on a file that not exist (and it will not be created)
    print 'STEP 2 Test POST method on a file that not exist'
    resp = requests.post(hostUrl + '/' + randomFile)
    if resp.status_code == 404 and not os.path.exists(fullPath) :
        print 'DONE <SUCCESS>'
    else:
        if os.path.exists(fullPath):
            createdFiles.append(fullPath)
        print 'DONE <FAIL>'

    #STEP 3 Test PUT method in order to create a file
    print 'STEP 3 Test PUT method in order to create a file'
    dataBody =  "dummyContent1"
    resp = requests.put(url=(hostUrl + '/' + randomFile), data=dataBody)
    if resp.status_code == 200:
        print 'DONE <SUCCESS>'
        createdFiles.append(fullPath)
    else:
        print 'DONE <FAIL>'

    #STEP 4 Test GET to see if the file was created
    print 'STEP 4 Test GET to see if the file was created'
    resp = requests.get(url=(hostUrl + '/' + randomFile))
    if resp.status_code == 200:
        print 'DONE <SUCCESS>'
    else:
        print 'DONE <FAIL>'

    #this is the cleanup part. Here all files that were created are deleted from the filesystem.
    for i in range(len(createdFiles)):
        if os.path.exists(createdFiles[i]):
            os.remove(createdFiles[i])


else:
    print ('Program arguments not provided <ip> <port> <ServerPath>')