# T2R2 Microservices 

## Building and starting up
To build the project
```shell
sbt clean compile
```

## Deploying and running locally
```shell
sbt run
```
For some reason, we might need to run it this way:
```shell
export HOST=0.0.0.0 ; sbt run
```

```shell
docker compose up
```
    
## Test on local machine 

### Test with curl
The ```-plaintext``` argument is to disable TLS

```shell
curl \
  -XPOST \
  -H "Content-Type: application/json" \
  -d '{"jobDefinitionId": "com.llaama.calc@test1@1.0.1"}' \
  localhost:9000/com.llaama.t2r2.services.JobDefinitionService/GetDefinition
  ```

### Test with grpcurl (GRPC)
```shell
grpcurl GRPCURL_OPT T2R2_SERVICE list
```

```shell
grpcurl GRPCURL_OPT T2R2_SERVICE describe com.llaama.t2r2.api.JobDefinitionService
```

```shell
  grpcurl -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1"}'  -plaintext localhost:9000 com.llaama.t2r2.api.JobDefinitionService/GetDefinition
```

### Examples of curl calls

#### Post: create new JobDefinition
Job definition must always contain an ID (jobDefinitionId) in the form of package_name@app_short_name@version. 

This ID must be unique and will be parsed to extract organization (package name), shortName and buildVersion. 

These are key elements to ensure T2R2. 

```shell
curl -XPOST -H "Content-Type: application/json" -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1", "name" : "test 1", "ownerEmail" : "bd@qbddd.com" }' localhost:9000/definitions/new
```

#### Post: add properties 
```shell
curl -XPOST -H "Content-Type: application/json" -d '{"key" : "k1", "value" : "val1" }' localhost:9000/definitions/com.llaama.calc@test1@1.0.1/properties/add
```

#### Get
```shell
curl -XGET localhost:9000/definitions/com.llaama.calc@test1@1.0.1 | jq
```


## Deploying on Kalix

### To publish to docker hub
```shell
sbt clean compile docker:publish
```


### To deploy to kalix
```shell
kalix service deploy t2r2-services llaamasas/t2r2-services-definitions:1.0.0
```

### To expose a route
```shell
kalix service expose --enable-cors t2r2-services
```

## Remote calls using GRPC
                                                  
#### current hostname: ```autumn-lab-0230.us-east1.kalix.app```

### Listing all services
```shell
grpcurl $GRPCURL_OPT $T2R2_SERVICE list
```

### Describing a service (returns all possible rpc)
```shell
grpcurl $GRPCURL_OPT $T2R2_SERVICE describe com.llaama.t2r2.api.JobDefinitionService
```

### Add a new Job definition with curl - Rest API
```shell
curl -XPOST -H "Content-Type: application/json" -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1", "name" : "test 1", "ownerEmail" : "bd@qbddd.com" }' $T2R2_SERVICE $GRPCURL_OPT/definitions/new
```

### Add a new Job definition with grpcurl
```shell
grpcurl -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1", "name" : "test 1", "ownerEmail" : "bd@qbddd.com" }' $GRPCURL_OPT $T2R2_SERVICE com.llaama.t2r2.api.JobDefinitionService/NewDefinition
```

                           
### Get all definitions (using a view)
```shell
grpcurl $GRPCURL_OPT $T2R2_SERVICE com.llaama.t2r2.view.QueryDefinitions/GetDefinitions
```

### Get details about one specific Job definition
```shell
grpcurl -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1"}' $GRPCURL_OPT $T2R2_SERVICE com.llaama.t2r2.api.JobDefinitionService/GetDefinition
```

### Add a property for a Job Definition
```shell
grpcurl -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1", "key" : "k1", "value" : "val1" }' $GRPCURL_OPT $T2R2_SERVICE com.llaama.t2r2.api.JobDefinitionService/AddProperty
```

### Remove a property
```shell
grpcurl -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1", "key" : "k1" }' $GRPCURL_OPT $T2R2_SERVICE com.llaama.t2r2.api.JobDefinitionService/RemoveProperty
```

### Add a digest for a Job Definition
```shell
grpcurl -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1", "key" : "main.code", "digest" : "9ffa0716eabc5eda35c8936fb2a9f04e07c5e57942336be770c8bddb15222f9d" }' $GRPCURL_OPT $T2R2_SERVICE com.llaama.t2r2.api.JobDefinitionService/AddDigest
```

### Remove a digest
```shell
grpcurl -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1", "main.code" : "k1" }' $GRPCURL_OPT $T2R2_SERVICE com.llaama.t2r2.api.JobDefinitionService/RemoveDigest
```

### Add a parameter for a Job Definition
```shell
grpcurl -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1", "intParameter": {"key" : "preval1", "order" : 1, "name" : "pre. value", "hint" : "input", "minValue": 1, "maxValue": 10, "defaultValue" : 5}}' \
$T2R2_SERVICE $GRPCURL_OPT com.llaama.t2r2.api.JobDefinitionService/AddIntParameter

grpcurl -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1", "floatParameter": {"key" : "preval1", "order" : 1, "name" : "pre. value", "hint" : "input", "minValue": 1.0, "maxValue": 100.0, "defaultValue" : 25.5}}' \
$T2R2_SERVICE $GRPCURL_OPT com.llaama.t2r2.api.JobDefinitionService/AddFloatParameter

grpcurl -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1", "textParameter": {"key" : "preval1", "order" : 1, "name" : "pre. value", "hint" : "input", "defaultValue" : "hello world"}}' \
$T2R2_SERVICE $GRPCURL_OPT com.llaama.t2r2.api.JobDefinitionService/AddTextParameter

grpcurl -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1", "optionParameter": {"key" : "preval1", "order" : 1, "name" : "pre. value", "hint" : "input", "options": ["hello mars", "hello moon", "hello world"], "allowsNew": false, "defaultValue" : "hello world"}}' \
$T2R2_SERVICE $GRPCURL_OPT com.llaama.t2r2.api.JobDefinitionService/AddOptionParameter
```

### Remove a parameter
```shell
grpcurl -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1", "key" : "k1" }' $GRPCURL_OPT $T2R2_SERVICE com.llaama.t2r2.api.JobDefinitionService/RemoveIntParameter
grpcurl -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1", "key" : "k1" }' $GRPCURL_OPT $T2R2_SERVICE com.llaama.t2r2.api.JobDefinitionService/RemoveFloatParameter
grpcurl -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1", "key" : "k1" }' $GRPCURL_OPT $T2R2_SERVICE com.llaama.t2r2.api.JobDefinitionService/RemoveTextParameter
grpcurl -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1", "key" : "k1" }' $GRPCURL_OPT $T2R2_SERVICE com.llaama.t2r2.api.JobDefinitionService/RemoveOptionParameter
```

### Seal a Job Definition, afterwards nothing can be changed anymore !
```shell
grpcurl -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1"}' $GRPCURL_OPT $T2R2_SERVICE com.llaama.t2r2.api.JobDefinitionService/Seal
```

### Add a new Job with grpcurl
```shell
grpcurl -d '{"jobDefinitionId" : "com.llaama.calc@test1@1.0.1", "name" : "test job 1", "ownerEmail" : "bd@qbddd.com", "description" : "Test11", "organization" : "com.llaama",  "timeout" : 100, "clonedFrom": ""}' $GRPCURL_OPT $T2R2_SERVICE com.llaama.t2r2.api.NewJobAction/CreateNewJob
```

### Add features to a job
```shell
grpcurl -d '{"jobId" : "", "key" : "key001", "property" : "hello world"}' $GRPCURL_OPT $T2R2_SERVICE com.llaama.t2r2.api.JobService/AddProperty
```

### Get all jobs from view 
```shell
grpcurl $GRPCURL_OPT $T2R2_SERVICE com.llaama.t2r2.view.QueryJobs/GetJobs
```

### Get one job 
```shell
grpcurl -d '{"jobId" : ""}' $GRPCURL_OPT $T2R2_SERVICE com.llaama.t2r2.api.JobService/RetrieveJob




