CREATE TABLE IF NOT EXISTS tblMonitorRequests (
    id SERIAL PRIMARY KEY,
    userId VARCHAR(50) NOT NULL,
    requestingDevice VARCHAR(50) NOT NULL,
    requestGranted INT NOT NULL
);