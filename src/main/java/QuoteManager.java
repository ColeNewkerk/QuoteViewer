import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import domain.Quote;
import exceptions.QuoteException;
import exceptions.QuoteIdExistsException;
import exceptions.QuoteIdNotExistsException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class QuoteManager {

    Dao<Quote, Integer> quoteDao;

    public QuoteManager() {
        this("quoteHandson.db");
    }

    public QuoteManager(String dbName) {
        try {
            var connectionSource = new JdbcConnectionSource("jdbc:sqlite:" + dbName);
            TableUtils.createTableIfNotExists(connectionSource, Quote.class);
            this.quoteDao = DaoManager.createDao(connectionSource, Quote.class);
        } catch (SQLException e) {
            throw new QuoteException("Something related to connection happened!", e);
        }
    }

    /**
     * @param id
     * @param quote
     * @param from
     * @return
     */
    public Quote addQuote(int id, String quote, String from) {
        try{
            for(int i = 0; i < 10; i++){
                quoteDao.create(new Quote(i,"quote: " + quote, "from " + from));
            }
        }
        catch (SQLException sqle){
        }

        return addQuote(new Quote(id, quote, from));

    }

    /**
     * DON'T CHANGE THE SIGNATURE!
     *
     * @param quote
     * @return
     * @throws QuoteIdExistsException when the id of the new quote is already in the DB.
     * @throws QuoteException         for any other exceptions.
     */
    public Quote addQuote(Quote quote) {
        // TODO

        return null;

    }

    /**
     * DON'T CHANGE THE SIGNATURE!
     *
     * @return
     * @throws QuoteException for any other exceptions.
     */
    public List<Quote> getAllQuotes() {
        // TODO
        try{
            var allQuotes = quoteDao.queryForAll();
            for(var quote : allQuotes){
                System.out.println(quote);
            }
        }catch (QuoteException quoteException){

        }
        catch(SQLException sqle){

        }
        return null;

    }

    /**
     * Update 'quote' part of a quote of quote with 'id'
     *
     * @param id
     * @param quote
     * @return the updated quote
     * @throws QuoteIdNotExistsException when id doesn't exist
     * @throws QuoteException            for any other exceptions.
     */
    public Quote updateQuoteText(int id, String quote) {
        try {
            if (quoteDao.idExists(id)) {
                var theQuote = quoteDao.queryForId(id);
                quoteDao.update(new Quote(id, quote, theQuote.getFrom()));
                return quoteDao.queryForId(id);
            } else {
                throw new QuoteIdNotExistsException(id);
            }
        } catch (SQLException e) {
            throw new QuoteException("Something happened on id check and update!", e);
        }
    }

    /**
     * Update 'from' part of a quote of quote with 'id'
     * DON'T CHANGE THE SIGNATURE!
     *
     * @param id
     * @param from
     * @return the updated quote
     * @throws QuoteIdNotExistsException when id doesn't exist
     * @throws QuoteException            for any other exceptions.
     */
    public Quote updateQuoteFrom(int id, String from) {
        // TODO
        try{
            if(quoteDao.idExists(id)){
                var theFrom = quoteDao.queryForId(id);
                quoteDao.update(new Quote(id,theFrom.getQuote(),from));
                return quoteDao.queryForId(id);
            }else{
                throw new QuoteIdNotExistsException(id);
            }
        }
        catch(SQLException e){
            throw new QuoteException("Something happened on id check and update",e);

        }

    }

    /**
     * DON'T CHANGE THE SIGNATURE!
     *
     * @param id
     * @return a copy of the delete quote
     * @throws QuoteIdNotExistsException when id doesn't exist
     * @throws QuoteException            for any other exceptions.
     */
    public Quote deleteQuote(int id) {
        // TODO
        try{
            if(quoteDao.idExists(id)){
                var theQuote = quoteDao.queryForId(id);
                quoteDao.delete(theQuote);
            }else{
                throw new QuoteIdNotExistsException(id);
            }
        }
        catch(SQLException e){
            throw new QuoteException("Something happened on id check and update",e);
        }
        return null;
    }

    public void disposeResources() {
        try {
            quoteDao.getConnectionSource().close();
        } catch (IOException e) {
            throw new QuoteException("Couldn't close the source!", e);
        }
    }
}
