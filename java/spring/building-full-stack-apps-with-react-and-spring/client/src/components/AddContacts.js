import { Component, createRef } from "react";

export default class AddContacts extends Component {

    constructor(props) {
        super(props);
        this.firstName = createRef();
        this.lastName = createRef();
        this.email = createRef();
    }

    submitContact(event) {
        event.preventDefault();

        let contact = {
            firstName: this.firstName.current.value,
            lastName: this.lastName.current.value,
            email: this.email.current.value
        }

        fetch("http://localhost:8080/api/contacts", {
            method: "POST",
            headers: {
                "content-type": "application/json"
            },
            body: JSON.stringify(contact)
        })
        .then(response => response.json());

        window.location.reload();
    }

    render() {
        return (
            <div className="row">
                <form className="col s12" onSubmit={this.submitContact.bind(this)}>
                <div className="row">
                    <div className="input-field col s6">
                        <input ref={this.firstName} type="text" className="validate" />
                        <label htmlFor="firstName">First Name</label>
                    </div>
                    <div className="input-field col s6">
                        <input ref={this.lastName} type="text" className="validate" />
                        <label htmlFor="lastName">Last Name</label>
                    </div>
                </div>
                <div className="row">
                    <div className="input-field col s12">
                        <input ref={this.email} type="email" className="validate" />
                        <label htmlFor="email">Email</label>
                    </div>
                </div>
                <div className="row">
                    <button className="waves-effect waves-light btn" type="submit" name="action">Submit</button>
                </div>
                </form>
            </div>
        )
    }
}