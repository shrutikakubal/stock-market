import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { environment } from '../../environments/environment';
import {Company} from '../model/company.model'

import { Observable } from 'rxjs';
import {Ipo} from '../model/ipo.model'
import {HttpParams} from "@angular/common/http";
import {Stock} from '../model/stock.model'

    

@Injectable({providedIn: 'root'})
export class CompanyService {

    constructor(private http: HttpClient) { }

    baseUrl: string = environment.getBaseUrl('com');

    createCompany(company: Company): Observable<Company> {
        return this.http.post<Company>(this.baseUrl + 'company/admin/new', company);
      }
    
      updateCompany(companyName: string, company: Company): Observable<Company> {
        if (!companyName) { return new Observable(); }
        return this.http.put<Company>(this.baseUrl + 'company/admin/update/' + companyName, company);
      }
    
      updateCompanyActive(companyName: string, status: string): Observable<Company> {
        if (!companyName) { return new Observable(); }
        status=status.trim();
        const options = status ?
        { params: new HttpParams().set('status', status) } : {}
        return this.http.put<Company>(this.baseUrl + 'company/admin/activate/' + companyName, '', options);
      }
    
      getCompanies(): Observable<Company[]> {
        return this.http.get<Company[]>(this.baseUrl + 'company/all');
      }
    
      getByCompanyName(companyName: string): Observable<Company> {
        if (!companyName) { return new Observable(); }
        return this.http.get<Company>(this.baseUrl + 'company/name/' + companyName);
      }
    
      getByStockCode(stockCode: string): Observable<Company> {
        if (!stockCode) { return new Observable(); }
        return this.http.get<Company>(this.baseUrl + 'company/code/' + stockCode);
      }
    
      getBySectorName(sectorName: string): Observable<Company[]> {
        if (!sectorName) { return new Observable(); }
        return this.http.get<Company[]>(this.baseUrl + 'company/sector/' + sectorName);
      }
    
      getByStockExchange(stockExchange: string): Observable<Company[]> {
        if (!stockExchange) { return new Observable(); }
        return this.http.get<Company[]>(this.baseUrl + 'company/exchange/' + stockExchange);
      }
    
      findCompanyContainingName(companyName: string): Observable<Company[]> {
        if (!companyName) { return new Observable(); }
        return this.http.get<Company[]>(this.baseUrl + 'company/findByName/' + companyName);
      }
    
      findCompanyContainingCode(stockCode: string): Observable<Company[]> {
        if (!stockCode) { return new Observable(); }
        return this.http.get<Company[]>(this.baseUrl + 'company/findByStockCode/' + stockCode);
      }
    
      getCompaniesLikeDebounce(debounce: string): Observable<Company[]> {
        if (!debounce) { return new Observable(); }
        if (debounce.match(/^\d/)) {
          console.log('getCompaniesLikeCriteria-number search');
          return this.findCompanyContainingCode(debounce);
        } else {
          console.log('getCompaniesLikeCriteria-string search');
          return this.findCompanyContainingName(debounce);
        }
      }
      // IpoService
      addNewIpo(ipo: Ipo): Observable<Ipo> {
        return this.http.post<Ipo>(this.baseUrl + 'ipo/admin/new', ipo);
      }
    
      updateIpo(companyName: string, ipo: Ipo): Observable<Ipo> {
        if (!companyName) { return new Observable(); }
        return this.http.put<Ipo>(this.baseUrl + 'ipo/admin/update/' + companyName, ipo);
      }
    
      getAllIpo(): Observable<Ipo[]> {
        return this.http.get<Ipo[]>(this.baseUrl + 'ipo/all');
      }
    
      getIpoByName(companyName: string): Observable<Ipo> {
        if (!companyName) { return new Observable(); }
        return this.http.get<Ipo>(this.baseUrl + 'ipo/name/' + companyName);
      }

      // stockPriceService
      getCurrent(companyCode: string): Observable<Stock> {
        if (!companyCode) { return new Observable(); }
        return this.http.get<Stock>(this.baseUrl + 'stock/current/' + companyCode);
      }
    
  
      getAllStocksByCompany(companyCode: string): Observable<Stock[]> {
        if (!companyCode) { return new Observable(); }
        return this.http.get<Stock[]>(this.baseUrl + 'stock/all/' + companyCode);
      }
    
      getAllStocksBetween(companyCode: string, params: object): Observable<Stock[]> {
        if (!companyCode) { return new Observable(); }
        return this.http.post<Stock[]>(this.baseUrl + 'stock/between/'+companyCode, params);
    }

}
