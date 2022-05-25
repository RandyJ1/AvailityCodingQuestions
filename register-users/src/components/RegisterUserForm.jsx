import React from 'react';

class RegisterUserForm extends React.Component {
    state = {
        name: "",
        npiNumber: "",
        address: "",
        number: "",
        email: "",
    };

    add = (e) => {
        e.preventDefault();
        if(this.state.name === '' || this.state.npiNumber === '' ||
           this.state.address === '' || this.state.number === '' ||
           this.state.email === '') {
               alert('All fields are required!');
               return;
           }
        this.props.addUserHandler(this.state);
        this.setState({
            name: "",
            npiNumber: "",
            address: "",
            number: "",
            email: ""
        });
    };
    
    render() {
        return (
            <form onSubmit={this.add}>
                <label>Name:
                    <input 
                        type="text" 
                        value={this.state.name}
                        onChange={(e) => this.setState({ name: e.target.value })}
                    />
                </label>
                <br/>
                <label>NPI number:
                    <input 
                        type="text" 
                        value={this.state.npiNumber}
                        onChange={(e) => this.setState({ npiNumber: e.target.value })}
                    />
                </label>
                <br/>
                <label>Business Address:
                    <input 
                        type="text" 
                        value={this.state.address}
                        onChange={(e) => this.setState({ address: e.target.value })}
                    />
                </label>
                <br/>
                <label>Telephone Number:
                    <input 
                        type="text" 
                        value={this.state.number}
                        onChange={(e) => this.setState({ number: e.target.value })}
                    />
                </label>
                <br/>
                <label>Email:
                    <input 
                        type="text" 
                        value={this.state.email}
                        onChange={(e) => this.setState({ email: e.target.value })}
                    />
                </label>
                <br/>
                <input type="submit" />
            </form>
        )
    }
}

export default RegisterUserForm;