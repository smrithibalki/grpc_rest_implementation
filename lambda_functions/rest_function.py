import json
import boto3
import botocore
import linecache
import datetime
import re
import hashlib
from datetime import datetime
def lambda_handler(event, context):
    # TODO implement
    s3 = boto3.resource('s3')

    try:
        s3.Bucket("homework3buck").download_file("logFileGenerator.log", '/tmp/local_log.log')
    except botocore.exceptions.ClientError as e:
        if e.response['Error']['Code'] == "404":
            print("The object does not exist.")
        else:
            raise
    # line = linecache.getline("/tmp/local_log.log", 5).rstrip()
    # print(str(event['stringQueryParameters']['dt']))
    # print(str(event['stringQueryParameters']['pattern']))
    length = 100
    LB = event["queryStringParameters"]['lower_interval']
    UB = event["queryStringParameters"]['upper_interval']
    #str_pattern = event["queryStringParameters"]['pattern']
    print(LB , UB)
    
    def binarySearch(val):
        val1 = datetime.strptime(val,"%H:%M:%S").time()
        lo, hi = 1, 206
        best_ind = lo
        while lo <= hi:
            mid = int((lo + hi) / 2)
            
            try:
                line = (linecache.getline("/tmp/local_log.log", mid).rstrip()).split(" ")
                x = (datetime.strptime(line[0],"%H:%M:%S.%f").replace(microsecond=0)).time()
                
            except:
                pass
            if x < val1:
           
                lo = mid + 1
            elif x > val1:
          
                hi = mid - 1
            else:
                best_ind = mid
                break

        # check if data[mid] is closer to val than data[best_ind] 
        
        return best_ind
    
    index1 = binarySearch(LB)
    print("index 1 is ",index1)
    
    UB1 = datetime.strptime(UB,"%H:%M:%S").time()
    timestamp_regex = r'\d{2}:\d{2}:\d{2}.\d{3}'
    str_pattern = r'[\w]+'
    fp = open("/tmp/local_log.log")
    list_msgs=[]
    index = index1
    for index,line in enumerate(fp):
        try:
            value = (datetime.strptime((line.split(" ")[0]),"%H:%M:%S.%f").replace(microsecond=0)).time()

            if  value.minute == UB1.minute:
                
                break
            else:
                msg = line.split(" ")[5]
                check = re.match(str_pattern, msg)
                if check:
                    list_msgs.append((hashlib.md5(msg.encode()).hexdigest()))
                #print(msg)
        except:
            pass        
            
            
    if len(list_msgs)==0:
        return {
            'statusCode' : 404,
            'body' : json.dumps("Error 404")
        }
    else:
        
        return {
        'statusCode': 200,
        'body': json.dumps(list_msgs)
        }
   
