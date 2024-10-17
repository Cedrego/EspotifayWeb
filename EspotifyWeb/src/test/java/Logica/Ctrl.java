/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;
import Persistencia.ControladoraPersistencia;
import Presentacion.DataAlbum;
import Presentacion.DataArtista;
import Presentacion.DataCliente;
import Presentacion.DataGenero;
import Presentacion.DataParticular;
import Presentacion.DataPorDefecto;
import Presentacion.DataTema;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Ctrl implements ICtrl{
    
    ControladoraPersistencia controlPersist = new ControladoraPersistencia();
    //Creadores
    @Override
    public void crearCliente(DataCliente cli){
        controlPersist.crearCliente(new Cliente(cli.getNickname(), cli.getNombre(),cli.getApellido(),cli.getCorreo(),cli.getPassword() ,cli.getFecha()));
    }
    @Override
    public void crearArtista(DataArtista art){
        controlPersist.crearArtista(new Artista(art.getNickname(),art.getNombre(),art.getApellido(),art.getCorreo(),art.getPassword(),art.getFecha(),art.getBiografia(),art.getSitioWeb()));
    }
    @Override
    public void crearTema(DataTema gen){
        controlPersist.crearTema(new Tema(gen.getNombre(), gen.getDuracion(), gen.getOrdenAlbum(), gen.getDireccion()));
    }
    @Override
    public void crearGenero(DataGenero gen){
        controlPersist.crearGenero(new Genero(gen.getNombre(),controlPersist.findGenero(gen.getPadre())));
    }
    @Override
    public void crearAlbum(DataAlbum alb){
        controlPersist.crearAlbum(new Album(alb.getNombre(),alb.getArtista(),alb.getCreacion()));
    }
    @Override
    public void crearParticular(DataParticular part){
        controlPersist.crearParticular(new Particular(part.getNombre(),controlPersist.findCliente(part.getCliente())));
    }
    @Override
    public void crearporDefecto(DataPorDefecto pordef){
        controlPersist.crearporDefecto(new porDefecto(pordef.getNombre(),controlPersist.findGenero(pordef.getGenero())));
    }
    
    //Getters
    @Override
    public List<DataCliente> traerClientes(){
        List<DataCliente> DTCli = new ArrayList();
        for (Cliente cliente : controlPersist.traerClientes()) {
            DataCliente cli = new DataCliente(cliente.getNickname(), cliente.getNombre(), cliente.getApellido(), cliente.getCorreo(), cliente.getPassword(), cliente.getFecha());
            List<String> tems = new ArrayList();
            for (Tema tem : cliente.getTemasFAV()) {
                tems.add(tem.getNombre());
            }
            cli.setDataTemaFav(tems);
            List<String> CSeguidos = new ArrayList();
            List<String> CSeguidores = new ArrayList();
            List<String> ASeguidos = new ArrayList();
            for (Artista art : cliente.getArtSigueA()) {
                ASeguidos.add(art.getNickname());
            }
            for (Cliente cliS : cliente.getCliSigueA()) {
                CSeguidos.add(cliS.getNickname());
            }
            for (Cliente cliSs : cliente.getSeguidoPor()) {
                CSeguidores.add(cliSs.getNickname());
            }
            cli.setDataArtSeguido(ASeguidos);
            cli.setDataCliSeguido(CSeguidos);
            cli.setDataCliSeguidor(CSeguidores);
            List<String> DTAlb = new ArrayList();
            for (Album alb : cliente.getAlbumFav()) {
                DTAlb.add(alb.getNombre());
            }
            cli.setDataAlmFav(DTAlb);
            List<String> particulares = new ArrayList();
            for(Particular partA : cliente.getParticular()){
                particulares.add(partA.getNombre());
            }
            cli.setDataPart(particulares);            
            List<String> particularesB = new ArrayList();
            for(Particular partB : cliente.getPlayFavPart()){
                particularesB.add(partB.getNombre());
            }
            cli.setDataPartFav(particularesB);
            List<String> porDefecto = new ArrayList();
            for(porDefecto pd : cliente.getPlayFavPD()){
                porDefecto.add(pd.getNombre());
            }
            cli.setDataPorDefFav(porDefecto);
            DTCli.add(cli);
        }
        return DTCli;
    }
    @Override
    public List<DataArtista> traerArtistas(){
       List<DataArtista> Final = new ArrayList();
        for(Artista arts : controlPersist.traerArtistas()){
            DataArtista art = new DataArtista(arts.getNickname(),arts.getNombre(),arts.getApellido(),arts.getCorreo(),arts.getPassword(),arts.getFecha(),arts.getBiografia(),arts.getSitioWeb());
            List<String> seguidores = new ArrayList();
            for(Cliente cli : arts.getSeguidoPorA()){
                seguidores.add(cli.getNickname());
            }
            art.setDataseguidoPorA(seguidores);
            List<DataAlbum> DTAlb = new ArrayList();
            for(Album alb : arts.getAlbumes()){
                List<DataTema> temsAlb = new ArrayList();
                for (Tema tem : alb.getTemas()) {
                    List<String> DTGenB = new ArrayList();
                    for (Genero gen : tem.getGeneros()) {
                        DTGenB.add(gen.getPadre().getNombre());
                    }
                    temsAlb.add(new DataTema(tem.getNombre(), tem.getDuracion(), tem.getOrdenAlbum(), tem.getDireccion(), DTGenB));
                }
                List<String> DTGenC = new ArrayList();
                for (Genero gen : alb.getGeneros()) {
                    DTGenC.add(gen.getPadre().getNombre());
                }
                DTAlb.add(new DataAlbum(alb.getNombre(), alb.getArtista(), alb.getCreacion(), temsAlb, DTGenC));
            }
            art.setDataalbumes(DTAlb);
            Final.add(art);
        }
        return Final;
    }
    @Override
    public List<DataGenero> traerGeneros(){
        List<DataGenero> DTGen = new ArrayList();
        for(Genero gen : controlPersist.traerGeneros()){
            DTGen.add(new DataGenero(gen.getNombre(),gen.getPadre().getNombre()));
        }
        return DTGen;
    }
    @Override
    public List<DataTema> traerTemas(){
        List<DataTema> tems = new ArrayList();
        for (Tema tem : controlPersist.traerTemas()) {
            List<String> DTGen = new ArrayList();
            for (Genero gen : tem.getGeneros()) {
                DTGen.add(gen.getPadre().getNombre());
            }
            tems.add(new DataTema(tem.getNombre(), tem.getDuracion(), tem.getOrdenAlbum(), tem.getDireccion(), DTGen));
        }
        return tems;
    }
    @Override
    public List<DataAlbum> traerAlbumes(){
        List<DataAlbum> DTAlb = new ArrayList();
        for (Album alb : controlPersist.traerAlbumes()) {
            List<DataTema> temsAlb = new ArrayList();
            for (Tema tem : alb.getTemas()) {
                List<String> DTGenB = new ArrayList();
                for (Genero gen : tem.getGeneros()) {
                    DTGenB.add(gen.getPadre().getNombre());
                }
                temsAlb.add(new DataTema(tem.getNombre(), tem.getDuracion(), tem.getOrdenAlbum(), tem.getDireccion(), DTGenB));
            }
            List<String> DTGenC = new ArrayList();
            for (Genero gen : alb.getGeneros()) {
                DTGenC.add(gen.getPadre().getNombre());
            }
            DTAlb.add(new DataAlbum(alb.getNombre(), alb.getArtista(), alb.getCreacion(), temsAlb, DTGenC));
        }
        return DTAlb;
    }
    @Override
    public List<DataParticular> traerParticulares(){
        return null/*controlPersist.traerParticulares()*/;
    }
    @Override
    public List<DataPorDefecto> traerporDefectos(){
        return null /*controlPersist.traerporDefectos()*/;
    }
}
