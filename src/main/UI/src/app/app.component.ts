import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {HttpClient, HttpResponse,HttpHeaders} from "@angular/common/http";
import {forkJoin, Observable, of} from 'rxjs';
import { map, tap, catchError } from "rxjs/operators";
import {formatCurrency} from "@angular/common";






@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  presentationTimes!: string[];

  constructor(private httpClient:HttpClient){}

  private baseURL:string='http://localhost:8080';

  private getUrl:string = this.baseURL + '/room/reservation/v1/';
  private postUrl:string = this.baseURL + '/room/reservation/v1';
  public submitted!:boolean;
  roomsearch! : FormGroup;
  rooms! : Room[];
  request!:ReserveRoomRequest;
  currentCheckInVal!:string;
  currentCheckOutVal!:string;

  welcomeMessages$!: Observable<{ english: string, french: string}>;
  easternTime:string = '';
  mountainTime!: string;
  utcTime!: string;
  timeZoneId!: string;


  ngOnInit(){
    this.roomsearch= new FormGroup({
      checkin: new FormControl(' '),
      checkout: new FormControl(' ')
    });

    //     this.rooms=ROOMS;


    const roomsearchValueChanges$ = this.roomsearch.valueChanges;

    // subscribe to the stream
    roomsearchValueChanges$.subscribe(x => {
      this.currentCheckInVal = x.checkin;
      this.currentCheckOutVal = x.checkout;
    });

    // testing to see if angular is fetching data from back end
    console.log("Fetching welcome messages...");
    this.welcomeMessages$ = this.getWelcomeMessages().pipe(
      tap(messages => console.log("Received messages:", JSON.stringify(messages, null, 2))),
      catchError(error => {
        console.error("Error fetching messages:", error);
        return [];
      })
    );

    this.fetchTimes();

  }

  onSubmit({value,valid}:{value:Roomsearch,valid:boolean}){
    this.getAll().subscribe(

      rooms => {console.log(Object.values(rooms)[0]);this.rooms=<Room[]>Object.values(rooms)[0]; }

  )}

  roomPriceConversion(price: number, currency: string) {

      const exchangeRates: { [key: string]: number } = {
        USD: 1,
        CAN: 1.25,
        EUR: 0.85
      };
      const convertedPrice = (price * exchangeRates[currency]).toFixed(2);
      return `${convertedPrice} ${currency}`;

  }


  reserveRoom(value:string){
    this.request = new ReserveRoomRequest(value, this.currentCheckInVal, this.currentCheckOutVal);

    this.createReservation(this.request);
  }
  createReservation(body:ReserveRoomRequest) {
    let bodyString = JSON.stringify(body); // Stringify payload
    let headers = new Headers({'Content-Type': 'application/json'}); // ... Set content type to JSON
    // let options = new RequestOptions({headers: headers}); // Create a request option

    const options = {
      headers: new HttpHeaders().append('key', 'value'),

    }

    this.httpClient.post(this.postUrl, body, options)
      .subscribe(res => console.log(res));
  }

  /*mapRoom(response:HttpResponse<any>): Room[]{
    return response.body;
  }*/

  getAll(): Observable<any> {
    return this.httpClient.get(this.baseURL + '/room/reservation/v1?checkin='+ this.currentCheckInVal + '&checkout='+this.currentCheckOutVal, {responseType: "json"})


  }

  //welcome messages for component.html
  getWelcomeMessages(): Observable<{ english: string, french: string}> {
    return this.httpClient.get<{ english: string, french: string}>('http://localhost:8080/api/welcome');
  }

  getPresentationTimes(): Observable<string[]> {
    const zones = ['eastern', 'mountain', 'utc']

    const requests: Observable<string>[] = zones.map(zone =>
      this.httpClient.get(`http://localhost:8080/api/timezone?timeZoneId=${zone}`, {responseType: 'text'}));

    return forkJoin(requests);
  }

  fetchTimes(): void {
    this.getPresentationTimes().subscribe(
      (responses) => {
        this.presentationTimes = responses;
        console.log('All timezones: ' + responses);
      }
    )
  }

}



export interface Roomsearch{
  checkin:string;
  checkout:string;
}




export interface Room{
  id:string;
  roomNumber:string;
  price:number;
  links:string;

}
export class ReserveRoomRequest {
  roomId:string;
  checkin:string;
  checkout:string;

  constructor(roomId:string,
              checkin:string,
              checkout:string) {

    this.roomId = roomId;
    this.checkin = checkin;
    this.checkout = checkout;
  }
}

/*
var ROOMS: Room[]=[
  {
  "id": "13932123",
  "roomNumber" : "409",
  "price" :"20",
  "links" : ""
},
{
  "id": "139324444",
  "roomNumber" : "509",
  "price" :"30",
  "links" : ""
},
{
  "id": "139324888",
  "roomNumber" : "609",
  "price" :"40",
  "links" : ""
}
] */
