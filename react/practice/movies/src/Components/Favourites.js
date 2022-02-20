import React, { Component } from 'react'

export default class Favourites extends Component {
    constructor() {
        super();
        this.state = {
            genres: [],
            currGen: 'All Genres',
            movies: [],
            currText: '',
            limit: 5,
            currPage: 1,
        }
    }

    componentDidMount() {
        let genreids = {28:'Action',12:'Adventure',16:'Animation',35:'Comedy',80:'Crime',99:'Documentary',18:'Drama',10751:'Family',14:'Fantasy',36:'History', 27:'Horror',10402:'Music',9648:'Mystery',10749:'Romance',878:'Sci-Fi',10770:'TV',53:'Thriller',10752:'War',37:'Western'};
        let data = JSON.parse(localStorage.getItem('movies-app') || '[]');
        let temp = [];
        data.forEach((movieObj) => {    
            temp.push(genreids[movieObj.genre_ids[0]]);
        });
        temp.unshift("All Genres");
        this.setState({
            genres: [...temp],
            movies: [...data],
        })
    }

    handleGenreChange = (g) => {
        this.setState({
            currGen: g
        })
    } 

    sortPopularityDesc = () => {
        let temp = this.state.movies;
        temp.sort((t, o) => {
            return o.popularity - t.popularity;
        });
        this.setState({
            movies: [...temp]
        })
    }

    sortPopularityAsc = () => {
        let temp = this.state.movies;
        temp.sort((t, o) => {
            return t.popularity - o.popularity;
        });
        this.setState({
            movies: [...temp]
        })
    }

    sortPopularityDesc = () => {
        let temp = this.state.movies;
        temp.sort((t, o) => {
            return o.vote_average - t.vote_average;
        })
    }

    sortPopularityAsc = () => {
        let temp = this.state.movies;
        temp.sort((t, o) => {
            return t.vote_average - o.vote_average;
        })
    }

    handlePageChange = (page) => {
        this.setState({
            currPage: page
        })
    }

    handleDelete = (id) => {
        let temp = [];
        temp = this.state.movies.filter((o) => {
            return o.id != id;
        })
        this.setState({
            movies: [...temp]
        })
        localStorage.setItem('movies-app', JSON.stringify(temp))
    }
    
    render() {
        let genreids = {28:'Action',12:'Adventure',16:'Animation',35:'Comedy',80:'Crime',99:'Documentary',18:'Drama',10751:'Family',14:'Fantasy',36:'History', 27:'Horror',10402:'Music',9648:'Mystery',10749:'Romance',878:'Sci-Fi',10770:'TV',53:'Thriller',10752:'War',37:'Western'};

        let filterarr = [];
        if (this.state.currText === '') {
            filterarr = this.state.movies;
        } else {
            filterarr = this.state.movies.filter((obj) => {
                let title = obj.original_title.toLowerCase();
                return title.includes(this.state.currText.toLowerCase());
            })
        }

        if (this.state.currGen != 'All Genres') {
            filterarr = this.state.movies.filter((obj) => {
                return genreids[obj.genre_ids[0]] == this.state.currGen;
            })
        }

        let pages = Math.ceil(filterarr.length / this.state.limit);
        let pagesarr = [];
        for (let i = 1; i <= pages; i++) {
            pagesarr.push(i);
        }
        // total length = 60;
        // pages = 12;
        // let at page 10 (idx wise 9): it will have movies from (45 to 49)
        // so we need to slice from 45 to 50
        let si = (this.state.currPage - 1) * this.state.limit;
        let ei = si + this.state.limit;
        filterarr = filterarr.slice(si, ei);

        return (
            <>
                    <div className="main">
                        <div className="row">
                            <div className="col-lg-3 col-sm-12">
                            <ul className="list-group favourites-genres">
                                {
                                    this.state.genres.map((genre)=>(
                                        this.state.currgen == genre ?
                                        <li className="list-group-item" style={{background:'#3f51b5',color:'white',fontWeight:'bold'}}>{genre}</li> :
                                        <li className="list-group-item" style={{background:'white',color:'#3f51b5'}} onClick={()=>this.handleGenreChange(genre)}>{genre}</li>
                                    ))
                                }
                            </ul>
                            </div>
                            <div className="col-lg-9 favourites-table col-sm-12">
                                <div className="row">
                                    <input type="text" className="input-group-text col" placeholder="Search" value={this.state.currText} onChange={(e)=>this.setState({currText:e.target.value})}/>
                                    <input type="number" className="input-group-text col" placeholder="Rows Count" value={this.state.limit} onChange={(e)=>this.setState({limit:e.target.value})}/>
                                </div>
                                <div className="row">
                                <table className="table">
                                    <thead>
                                        <tr>
                                        <th scope="col">Title</th>
                                        <th scope="col">Genre</th>
                                        <th scope="col"><i className="fas fa-sort-up" onClick={this.sortPopularityDesc}/>Popularity<i className="fas fa-sort-down" onClick={this.sortPopularityAsc}></i></th>
                                        <th scope="col"><i className="fas fa-sort-up" onClick={this.sortRatingDesc}></i>Rating<i className="fas fa-sort-down" onClick={this.sortRatingAsc}></i></th>
                                        <th scope="col"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {
                                            filterarr.map((movieObj)=>(
                                                <tr>
                                                    <td><img src={`https://image.tmdb.org/t/p/original${movieObj.backdrop_path}`} alt={movieObj.title} style={{width:'5rem'}}/> {movieObj.original_title}</td>
                                                    <td>{genreids[movieObj.genre_ids[0]]}</td>
                                                    <td>{movieObj.popularity}</td>
                                                    <td>{movieObj.vote_average}</td>
                                                    <td><button type="button" className="btn btn-danger" onClick={()=>this.handleDelete(movieObj.id)}>Delete</button></td>
                                                </tr>
                                            ))
                                        }
                                    </tbody>
                                    </table>
                                </div>
                                <nav aria-label="Page navigation example">
                                    <ul className="pagination">
                                        {
                                            pagesarr.map((page)=>(
                                                <li className="page-item"><a className="page-link" onClick={()=>this.handlePageChange(page)}>{page}</a></li>
                                            ))
                                        }
                                    </ul>
                                </nav>
                            </div>
                        </div>
                    </div>
                </>
        )
    }
}
