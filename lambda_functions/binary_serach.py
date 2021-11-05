
from concurrent import futures
import time
import datetime
from datetime import datetime
from datetime import timedelta
import logging
import json
import urllib.parse
import boto3
from boto3 import client
import grpc
import hello_pb2
import hello_pb2_grpc
import linecache
import json
import base64
import botocore
def lambda_handler(event, context):
    # TODO implement

    #s3 = boto3.client('s3',aws_access_key_id='AKIA562YIT6VZH4XLJNE',aws_secret_access_key='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')

    s3 = boto3.resource('s3')

    try:
        s3.Bucket("homework3buck").download_file("logFileGenerator.log", '/tmp/local_log.log')
    except botocore.exceptions.ClientError as e:
        if e.response['Error']['Code'] == "404":
            print("The object does not exist.")
        else:
            raise
    # line = linecache.getline("/tmp/local_log.log", 5).rstrip()
    

    #print("5th line is",line)
    
    a = base64.b64decode(event['body'])
    b = hello_pb2.Input()
    b.ParseFromString(a)
    time = b.time
    dt = b.dt
    print("TimeStamp: {} | Length :{} ".format(b.time,b.dt))

    def binarySearch(val):
        val = datetime.strptime(time,"%H:%M:%S").time()
        lo, hi = 1, int(dt)
        best_ind = lo
        while lo <= hi:
            mid = int((lo + hi) / 2)
            line = (linecache.getline("/tmp/local_log.log", mid).rstrip()).split(" ")
            try:
                x = (datetime.strptime(line[0],"%H:%M:%S.%f").replace(microsecond=0)).time()
                print(x)
                if x < val:
           
                    lo = mid + 1
                elif x > val:
          
                    hi = mid - 1
                else:
                    best_ind = mid
                    break
            except:
                print(line)
                pass    

        # check if data[mid] is closer to val than data[best_ind] 
        
        return best_ind
    
    ind = binarySearch(time)

    print("index",ind)
    time = (datetime.strptime(time,"%H:%M:%S").time()).minute
    print("time",time)
    line = (linecache.getline("/tmp/local_log.log", ind).rstrip()).split(" ")
    print(line)
    x = ((datetime.strptime(line[0],"%H:%M:%S.%f").replace(microsecond=0)).time()).minute
    print("line , x",line , x)
    if (time == x):
        print("yes")
        a=True
    else:
        print("no")
        a=False

    response = hello_pb2.Response(result=a)
    serialized = response.SerializeToString()
    
    result1 =  {
        'statusCode': 200,
        'body': serialized.decode()
    }
    return result1



