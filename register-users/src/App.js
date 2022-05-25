import RegisterUserForm from './components/RegisterUserForm';
import UserTable from './components/UserTable';
import { useState } from 'react';
import './App.css';

function App() {
  let [users, setUsers] = useState([])

  const addUserHandler = (user) => {
    console.log(user);
    setUsers([...users, user]);
  }

  return (
    <div className="App">
      <h1 className="App-header">REGISTER USER</h1>
      <hr/>
      <RegisterUserForm addUserHandler={addUserHandler}/>
      <br/>
      <UserTable users={users} addUser={setUsers}/>
    </div>
  );
}

export default App;
