import React, { Component } from 'react'

export default class Todo extends Component {
    constructor() {
        super();
        this.state = {
            tasks: [
                {
                    task: 'Check Mails',
                    key: 0,
                },
                {
                    task: 'Read Article',
                    key: 1,
                },
            ],
            currTask: ""
        }
    }

    handleChange = (e) => {
        // here if we will use normal function then our "this" will be gone
        // and then we have to use bind, call or apply
        this.setState({
            currTask: e.target.value
        })
    }

    handleSubmit = () => {
        this.setState({
            tasks: [...this.state.tasks, {task: this.state.currTask, key: this.state.tasks.length}],
            currTask: ''
        })
    }

    handleDelete = (key) => {
        let narr = this.state.tasks.filter((taskObj) => {
            return taskObj.key !== key;
        })
        this.setState({
            tasks: [...narr]
        })
    }
    
    render() {
        return (
            <div>
            <input type="text" value={this.state.currTask} onChange={this.handleChange} />
            <button onClick={this.handleSubmit}>Submit</button>
            <ol>
                {
                    this.state.tasks.map((taskObj) => (
                        <li key={taskObj.key}>
                            <p>{taskObj.task}</p>
                            <button onClick={() => this.handleDelete(taskObj.key)}>Delete</button>
                        </li>
                    ))
                }
            </ol>
            </div>
        );
    }
}
