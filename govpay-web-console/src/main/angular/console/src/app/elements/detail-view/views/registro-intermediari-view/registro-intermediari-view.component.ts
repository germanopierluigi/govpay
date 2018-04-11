import { AfterViewInit, Component, Input, OnInit } from '@angular/core';
import { IModalDialog } from '../../../../classes/interfaces/IModalDialog';
import { GovpayService } from '../../../../services/govpay.service';
import { UtilService } from '../../../../services/util.service';
import { Dato } from '../../../../classes/view/dato';
import { Standard } from '../../../../classes/view/standard';
import { Parameters } from '../../../../classes/parameters';
import { ModalBehavior } from '../../../../classes/modal-behavior';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/of';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Component({
  selector: 'link-registro-intermediari-view',
  templateUrl: './registro-intermediari-view.component.html',
  styleUrls: ['./registro-intermediari-view.component.scss']
})
export class RegistroIntermediariViewComponent implements IModalDialog, OnInit, AfterViewInit {

  @Input() informazioni = [];
  @Input() connettori = [];
  @Input() stazioni = [];

  @Input() json: any;
  @Input() modified: boolean = false;


  constructor(public gps: GovpayService, public us: UtilService) { }

  ngOnInit() {
    this.dettaglioRegistroIntermediari();
    this.elencoStazioni();
  }

  ngAfterViewInit() {
  }

  protected dettaglioRegistroIntermediari() {
    let _url = UtilService.URL_REGISTRO_INTERMEDIARI+'/'+encodeURIComponent(this.json.idIntermediario);
    this.gps.getDataService(_url).subscribe(
      function (_response) {
        this.json = _response.body;
        this.mapJsonDetail();
        this.gps.updateSpinner(false);
      }.bind(this),
      (error) => {
        //console.log(error);
        this.us.alert(error.message);
        this.gps.updateSpinner(false);
      });

  }

  protected mapJsonDetail() {
    //Riepilogo
    let _dettaglio = { informazioni: [], connettori: [] };
    _dettaglio.informazioni.push(new Dato({ label: 'Denominazione', value: this.json.denominazione }));
    _dettaglio.informazioni.push(new Dato({ label: 'ID intermediario', value: this.json.idIntermediario }));
    _dettaglio.informazioni.push(new Dato({ label: 'Principal pagoPa', value: this.json.principalPagoPa }));
    _dettaglio.informazioni.push(new Dato({ label: 'Abilitato', value: UtilService.ABILITA[this.json.abilitato.toString()] }));
    if(this.json.servizioPagoPa) {
      _dettaglio.connettori.push(new Dato({ label: 'URL', value: this.json.servizioPagoPa.url }));
      _dettaglio.connettori.push(new Dato({ label: 'Versione API', value: UtilService.TIPI_VERSIONE_API[this.json.servizioPagoPa.versioneApi] }));
      if(this.json.servizioPagoPa.auth) {
        _dettaglio.connettori.push(new Dato({ label: 'Tipo autenticazione', value: '' }));
        if(this.json.servizioPagoPa.auth.username) {
          _dettaglio.connettori.push(new Dato({label: 'Username', value: this.json.servizioPagoPa.auth.username }));
          _dettaglio.connettori.push(new Dato({label: 'Password', value: this.json.servizioPagoPa.auth.password }));
        }
        if(this.json.servizioPagoPa.auth.tipo) {
          _dettaglio.connettori.push(new Dato({label: 'Tipo', value: this.json.servizioPagoPa.auth.tipo }));
          _dettaglio.connettori.push(new Dato({label: 'KeyStore Location', value: this.json.servizioPagoPa.auth.ksLocation }));
          _dettaglio.connettori.push(new Dato({label: 'KeyStore Password', value: this.json.servizioPagoPa.auth.ksPassword }));
          if(this.json.servizioPagoPa.auth.tsLocation) {
            _dettaglio.connettori.push(new Dato({label: 'TrustStore Location', value: this.json.servizioPagoPa.auth.tsLocation }));
          }
          if(this.json.servizioPagoPa.auth.tsPassword) {
            _dettaglio.connettori.push(new Dato({label: 'TrustStore Password', value: this.json.servizioPagoPa.auth.tsPassword }));
          }
        }
      }
    }
    this.informazioni = _dettaglio.informazioni.slice(0);
    this.connettori = _dettaglio.connettori.slice(0);
  }

  protected elencoStazioni() {
    this.gps.getDataService(this.json.stazioni).subscribe(function (_response) {
        let _body = _response.body;
        this.stazioni = _body['risultati'].map(function(item) {
          let p = new Parameters();
          p.jsonP = item;
          p.model = this.mapNewItem(item);
          return p;
        }, this);
        this.gps.updateSpinner(false);
      }.bind(this),
      (error) => {
        this.gps.updateSpinner(false);
        this.us.alert(error.message);
      });
  }

  /**
   * Map item Stazione
   * @param item
   * @returns {Standard}
   */
  protected mapNewItem(item: any): Standard {
    let _std = new Standard();
    let _st = Dato.arraysToDato(
      ['Password', 'Abilitato'],
      [ UtilService.defaultDisplay({ value: item.password }), UtilService.defaultDisplay({ value: UtilService.ABILITA[(item.abilitato).toString()] }) ],
      ', '
    );
    _std.titolo = new Dato({ label: 'Id Stazione: ', value: item.idStazione });
    _std.sottotitolo = _st;

    return  _std;
  }

  protected _iconClick(ref: any, event: any) {
    let _ivm = ref.getItemViewModel();
    switch(event.type) {
      case 'edit':
        this._addEditStazione(true, _ivm.jsonP);
      break;
      case 'delete':
        console.log('delete');
      break;
    }
  }

  protected _editRegistro(event: any) {
    let _mb = new ModalBehavior();
    _mb.editMode = true;
    _mb.info = {
      viewModel: this.json,
      dialogTitle: 'Modifica intermediario',
      templateName: UtilService.INTERMEDIARIO
    };
    _mb.async_callback = this.save.bind(this);
    _mb.closure = this.refresh.bind(this);
    UtilService.blueDialogBehavior.next(_mb);
  }

  protected _addEditStazione(mode: boolean = false, _viewModel?: any) {
    let _mb: ModalBehavior = new ModalBehavior();
    _mb.editMode = mode;
    _mb.info = {
      viewModel: _viewModel,
      dialogTitle: (!mode)?'Nuova stazione':'Modifica stazione',
      templateName: UtilService.STAZIONE
    };
    _mb.async_callback = this.save.bind(this);
    _mb.closure = this.refresh.bind(this);
    UtilService.dialogBehavior.next(_mb);
  }

  refresh(mb: ModalBehavior) {
    this.modified = false;
    if(mb && mb.info && mb.info.viewModel) {
      this.modified = true;
      let p = new Parameters();
      let json = mb.info.viewModel;
      switch(mb.info.templateName) {
        case UtilService.STAZIONE:
          p.jsonP = json;
          p.model = this.mapNewItem(json);
          if(!mb.editMode) {
            this.stazioni.push(p);
          } else {
            this.stazioni.map((item) => {
              if (item.jsonP.idStazione == json.idStazione) {
                Object.keys(p.jsonP).forEach((key) => {
                  item.jsonP[key]= p.jsonP[key];
                });
                item.model = p.model;
              }
              return item;
            });
          }
        break;
        case UtilService.INTERMEDIARIO:
          this.json = json;
          this.mapJsonDetail();
        break;
      }
    }
  }

  /**
   * Save Registro intermediari|Stazioni (Put to: /intermediari/{idIntermediario} or
   * /intermediari/{idIntermediario}/stazioni/{idStazione} )
   * @param {BehaviorSubject<any>} responseService
   * @param {ModalBehavior} mb
   */
  save(responseService: BehaviorSubject<any>, mb: ModalBehavior) {
    let _service = UtilService.URL_REGISTRO_INTERMEDIARI;
    let json = mb.info.viewModel;
    let templateName = mb.info.templateName;
    if(templateName == UtilService.INTERMEDIARIO) {
      (mb.editMode)?json.stazioni = this.json.stazioni:null;
      _service += '/'+encodeURIComponent(json['idIntermediario']);
    }
    if(templateName == UtilService.STAZIONE) {
      _service += '/'+encodeURIComponent(this.json.idIntermediario);
      _service += UtilService.URL_STAZIONI+'/'+encodeURIComponent(json['idStazione']);
    }
    this.gps.saveData(_service, json).subscribe(
      () => {
        this.gps.updateSpinner(false);
        responseService.next(true);
      },
      (error) => {
        this.gps.updateSpinner(false);
        this.us.alert(error.message);
      });
  }

  title(): string {
    return UtilService.defaultDisplay({ value: this.json?this.json.denominazione:null });
  }

  infoDetail(): any {
    return {};
  }
}