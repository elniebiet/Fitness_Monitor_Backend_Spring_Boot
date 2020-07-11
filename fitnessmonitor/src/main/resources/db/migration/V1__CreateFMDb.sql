CREATE TABLE IF NOT EXISTS tblDevices (
    id SERIAL PRIMARY KEY,
    userId VARCHAR(50) NOT NULL,
    bodyTemp INTEGER,
    heartRate INTEGER,
    numOfSteps INTEGER
);

--CREATE TABLE IF NOT EXISTS tblMonitorRequests (
--    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
--    userId VARCHAR(50) NOT NULL REFERENCES tblDevices (userId)
--    requestingDevice VARCHAR(50) NOT NULL,
--    requestGranted INT NOT NULL
--);

