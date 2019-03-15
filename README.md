### Prerequisites
For being able to run the assignment, you need to have *docker* installed.

### Execution
1. Go to the root directory. You should be able to see `assignment` directory.

2. Run the following command in order to build the docker image needed for the rest of steps.

   ```shell
   $ ./setup-infra.sh
   ```

   Wait please until all dependencies are downloaded and packaged as the image.

3. Run the following commands in separate terminal sessions in order to run the services and the portal.

   ```shell
   $ ./run-persistence-service.sh
   $ ./run-account-service.sh
   $ ./run-transaction-service.sh
   $ ./run-portal.sh
   ```

4. You should see the following ports opened on your machines. These ports are forwarded to 8080 ports in the containers. All the containers are connected through a docker netwrok, so they can communicate internally.

   * Port 9000 exposing persistence service
   * Port 9001 exposing account service
   * Port 9002 exposing transaction service
   * Port 8080 serving the portal (UI)

5. Open a browser and navigate to http://localhost:8080. You should be able to see the portal's root page.

> PLEASE READ TODO FILE TO SEE THE IMPROVEMENTS WHICH CAN BE MADE.

Thanks.