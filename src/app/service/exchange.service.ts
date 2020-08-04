import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs/index';
import { StockExchange } from '../model/stockexchange.model';


@Injectable({
  providedIn: 'root'
})
export class ExchangeService {
    constructor(private http: HttpClient) { }

  baseUrl: string = environment.getBaseUrl('exc');

  addNew(exchange: StockExchange): Observable<StockExchange> {
    return this.http.post<StockExchange>(this.baseUrl + 'exchange/admin/new', exchange);
  }

  update(stockExchangeName: string, exchange: StockExchange): Observable<StockExchange> {
    if (!stockExchangeName) { return new Observable(); }
    return this.http.put<StockExchange>(this.baseUrl + 'exchange/admin/update/' + stockExchangeName, exchange);
  }

  getAll(): Observable<StockExchange[]> {
    return this.http.get<StockExchange[]>(this.baseUrl + 'exchange/admin/all');
  }

  getByName(stockExchangeName: string): Observable<StockExchange> {
    if (!stockExchangeName) { return new Observable(); }
    return this.http.get<StockExchange>(this.baseUrl + 'exchange/admin/name/' + stockExchangeName);
  }

  findCompanyList(stockExchangeName: string): Observable<StockExchange[]> {
    if (!stockExchangeName) { return new Observable(); }
    return this.http.get<StockExchange[]>(this.baseUrl + 'exchange/admin/find/' + stockExchangeName);
  }
}
