package tacos.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tacos.Taco;

import java.sql.*;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository {

  private JdbcTemplate jdbc;

  public JdbcTacoRepository(JdbcTemplate jdbc){
    this.jdbc = jdbc;
  }

  @Override
  public Taco save(Taco taco){
    long tacoId = saveTacoInfo(taco);
    taco.setId(tacoId);
    for(String ingredientId : taco.getIngredients()){
      saveIngredientToTaco(ingredientId, tacoId);
    }

    return taco;
  }

  private long saveTacoInfo(Taco taco){
    taco.setCreatedAt(new Date());
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbc.update(new PreparedStatementCreator() {
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
          PreparedStatement ps =
                connection.prepareStatement("insert into Taco (name, createdAt) values ( ?,? )", new String[] {"id"});
          ps.setString(1, taco.getName());
          ps.setTimestamp(2, new Timestamp(taco.getCreatedAt().getTime()));
          return ps;
        }
      }, keyHolder);

    return keyHolder.getKey().longValue();
  }

  private void saveIngredientToTaco(
    String ingredientId, long tacoId){
    jdbc.update("insert into Taco_Ingredients (taco, ingredient) values ( ?,? )",
      tacoId, ingredientId);
  }
}
