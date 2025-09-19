import { grpc,  StorageClient, Bucket } from "@nitric/suga-client";

const SERVICE_ADDRESS = `${process.env.SUGA_SERVICE_ADDRESS || "localhost:50051"}`;

export class SugaClient {
  private storageClient: InstanceType<typeof StorageClient>;
  
  public image: Bucket;
  

  constructor(credentials: grpc.ChannelCredentials = grpc.credentials.createInsecure()) {
    this.storageClient = new StorageClient(SERVICE_ADDRESS, credentials);
    
    this.image = new Bucket(this.storageClient, "image");
    
  }
} 