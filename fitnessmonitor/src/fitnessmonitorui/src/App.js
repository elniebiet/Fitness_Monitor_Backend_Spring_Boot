import React, {Component, Fragment} from 'react';
import Container from './Container';
import logo from './logo.svg';
import {getFitnessUpdate} from './UserFitnessUpdate';
import {getPermission} from './Permissions';
import './App.css';
import { render } from '@testing-library/react';
import {
  Table, Popconfirm, Button
} from 'antd';

class App extends Component {

  state = {
    fitnessResponse: {},
    requestingId: "USER000000",
    lblPermission: {},
    tblFitnessData: {}
  }

  componentDidMount () {
    document.getElementById("errorMessage").style.display = "none";
    this.state.lblPermission = document.getElementById("checkingPermission");
    this.state.lblPermission.style.display = "none";
    this.state.tblFitnessData = document.getElementById("fitnessTable");
  };

  btnCheckFitnessClicked = () => {
    let userid = document.getElementById("inUserId").value;
    let lblErrorMessage = document.getElementById("errorMessage");
    userid = userid.replace(" ", "");
    if(userid.length < 3){
      lblErrorMessage.style.display = "block";
      console.log("invalid input");
      return;
    }
    if ( /[^A-Za-z0-9 ]/.test(userid) ) { 
      lblErrorMessage.style.display = "block";
      console.log("invalid input");
      return;
    }
    userid = userid.toUpperCase();
    lblErrorMessage.style.display = "none";

    //request permission
    this.requestPermission(userid,this.state.requestingId).then(resp => {
      if(resp == 0){
        //permission not granted
        this.state.lblPermission.innerHTML = "permission not granted";
      } else { //resp == 2 permission granted
        this.state.lblPermission.innerHTML = "permission granted ...";
        //get fitness update
        this.state.lblPermission.innerHTML = "fetching data...";
        this.getFitness(userid).then(fitnessResp => {
          console.log(fitnessResp);
          let userRow = document.getElementById(userid);
          //if element doesnt exist already, add it
          if(userRow == null){ 
            let row = this.state.tblFitnessData.insertRow();
            row.setAttribute("id", userid);
            let userIdCell = row.insertCell(0);
            let bodyTempCell = row.insertCell(1);
            let heartRateCell = row.insertCell(2);
            let numOfStepsCell = row.insertCell(3);
            userIdCell.innerHTML = userid;
            bodyTempCell.innerHTML = fitnessResp.bodyTemp;
            heartRateCell.innerHTML = fitnessResp.heartRate;
            numOfStepsCell.innerHTML = fitnessResp.numOfSteps;
            let refreshButton = document.createElement('button');
            refreshButton.innerHTML = "Refresh";
            refreshButton.onclick = () => this.refreshFitness(userid);
            row.appendChild(refreshButton);
          } else {
            //else update existing one.

            userRow.children[1].innerHTML = fitnessResp.bodyTemp;
            userRow.children[2].innerHTML = fitnessResp.heartRate;
            userRow.children[3].innerHTML = fitnessResp.numOfSteps;
          
          }
          this.state.lblPermission.style.display = "none";
        });
      }
    });
    
  };

  requestPermission = (requesterId, requestingId) => new Promise((resolve, reject) => {
    let permissionResponse = 0;
    this.state.lblPermission.style.display = "block";
    this.state.lblPermission.innerHTML = "Requesting permission ...";
    getPermission(requesterId, requestingId).then(res => res.json().then(permissionJSON => {
      permissionResponse = permissionJSON.permissionStatus;
      resolve(permissionResponse);
      reject(permissionResponse);
    }));

  });

  refreshFitness = (userid) => {
    this.getFitness(userid).then(fitnessResp => {
      console.log(fitnessResp);
      let userRow = document.getElementById(userid);
     
      userRow.children[1].innerHTML = fitnessResp.bodyTemp;
      userRow.children[2].innerHTML = fitnessResp.heartRate;
      userRow.children[3].innerHTML = fitnessResp.numOfSteps;
     });
  };
  

  getFitness = (uid) => new Promise((resolve, reject) => {
    let jsonResponse = "";
    getFitnessUpdate(uid).then(res => res.json().then(fitnessJSON => {
      // console.log(fitnessJSON);
      jsonResponse = JSON.stringify(fitnessJSON);
      this.setState({
        fitnessResponse: jsonResponse
      });
      console.log(jsonResponse);
      resolve(fitnessJSON);
      reject(fitnessJSON);
    }));
  }); 

    
  
  
    render() {

        return (
          <Container>
            <h1>REMOTE FITNESS MONITORING SYSTEM</h1>
            <div id="searchUser">
              <input id="inUserId" type="text" placeholder="Enter User ID eg. USER2981"/>
              <button id="btnCheckFitness" onClick={this.btnCheckFitnessClicked}>Check Fitness</button>
              <label id="errorMessage">Invalid User ID</label>
              <label id="checkingPermission">Checking ...</label>
            </div>
            <table id="fitnessTable" class="responstable">
              
              <tr>
                <th>User ID</th>
                <th>Body Temperature</th>
                <th>Heart Rate</th>
                <th>Number of Steps</th>
                <th>Action</th>
              </tr>
              
              {/* <tr>
                <td>USER1284934</td>
                <td>35'c</td>
                <td>90</td>
                <td>200</td>
                <td><button>Refresh</button></td>
              </tr>
        */}
              
            </table>
          </Container>
        );
    }
}

export default App;
