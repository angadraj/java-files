import React, { Component } from 'react'
import axios from 'axios'
import {movies} from './getMovies'

export default class Movies extends Component {
    
    constructor() {
        super();
        this.state = {
            hover: '',
            movies: [],
            favourites: [],
            parr: [1],
            currPage: 1,
        }
    }

    handleFavourites = (movieObj) => {
        let oldData = JSON.parse(localStorage.getItem('movies-app') || '[]');
        if (this.state.favourites.includes(movieObj.id)) {
            oldData = oldData.filter((m) => movieObj.id !== m.id);
        } else {
            oldData.push(movieObj);
        }
        localStorage.setItem('movies-app', JSON.stringify(oldData));
        this.handleFavouritesState();
    }

    handleFavouritesState = () => {
        let oldData = JSON.parse(localStorage.getItem('movies-app') || '[]');
        let temp = oldData.map((m) => m.id);
        this.setState({
            favourites: [...temp]
        })
    }

    handleLeft = () => {
        console.log("handle left clicked");
        if (this.state.currPage != 1) {
            this.setState({
                currPage: this.state.currPage - 1,
            }, this.changeMovies);
        }
    }

    handleRight = () => {
        let temp = [];
        for (let i = 1; i <= this.state.parr.length + 1; i++) {
            temp.push(i);
        }
        console.log(temp);
        this.setState({
            parr: [...temp],
            currPage: this.state.currPage + 1
        }, this.changeMovies);
    }

    handleClick = (value) => {
        if (value != this.state.currPage) {
            this.setState({
                currPage: value
            }, this.changeMovies)
        }
    }

    changeMovies = async () => {
        const res = await axios.get(`https://api.themoviedb.org/3/movie/popular?api_key=5540e483a20e0b20354dabc2d66a31c9&language=en-US&page=${this.state.currPage}`)
        let data = res.data;
        console.log(data);
        this.setState({
            movies: [...data.results]
        })
    }

    async componentDidMount() {
        // side effects
        const res = await axios.get(`https://api.themoviedb.org/3/movie/popular?api_key=5540e483a20e0b20354dabc2d66a31c9&language=en-US&page=${this.state.currPage}`);
        let data = res.data;
        this.setState({
            movies: [...data.results],
        })
    }

    render() {
        return (
            <>
                {
                    this.state.movies.length === 0 ?
                    <div className="spinner-border text-primary" role="status">
                        <span class="visually-hidden">Loading...</span>
                    </div> :
                    <div>
                        <h3 className="text-center"><strong>Trending</strong></h3>
                        <div className="movies-list">
                            {
                                this.state.movies.map((movieObj) => (
                                    <div className="card movies-card" onMouseEnter={() => this.setState({hover: movieObj.id})} onMouseLeave={() => this.setState({hover: ''})}>
                                        <img src={`https://image.tmdb.org/t/p/original${movieObj.backdrop_path}`}  alt={movieObj.title} className="card-img-top movies-img" />
                                        <h5 className="card-title movies-title">{movieObj.original_title}</h5>
                                        <div className="button-wrapper" style={{display:'flex',width:'100%',justifyContent:'center'}}> 
                                            {
                                                this.state.hover === movieObj.id &&
                                                <a className="btn movies-btn button-primary" onClick={() => this.handleFavourites(movieObj)}>
                                                    {
                                                        this.state.favourites.includes(movieObj.id) ? "Remove from favourites" : "Add to favourites" 
                                                    }
                                                </a>
                                            }
                                        </div>
                                    </div>
                                ))
                            }
                        </div>
                        <div className="infinite-loader" style={{display:'flex',justifyContent:'center'}}>
                                <h2>Load More Movies .........................</h2>
                        </div>
                        <ul className="pagination">
                            <li className="page-item"><a class="page-link" onClick={this.handleLeft}>Previous</a></li>
                            {
                                this.state.parr.map((value) => (
                                    <li class="page-item"><a class="page-link" onClick={() => this.handleClick(value)}>{value}</a></li>
                                ))
                            }
                            <li className="page-item"><a class="page-link" onClick={this.handleRight}>Next</a></li>
                        </ul>
                    </div>
                
                }
            </>
        )
    }
}
