package cholog;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UpdatingDAO {
    private JdbcTemplate jdbcTemplate;

    public UpdatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
    private final RowMapper<Customer> actorRowMapper = (resultSet, rowNum) -> {
        Customer customer = new Customer(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name")
        );
        return customer;
    };
    추후 rowMapper에 대해 학습해보고 이용해보기
    */

    /**
     * public int update(String sql, @Nullable Object... args)
     */
    public void insert(Customer customer) {
        jdbcTemplate.update("insert into customers (first_name, last_name) values (?, ?)",
                customer.getFirstName(), customer.getLastName());
    }

    /**
     * public int update(String sql, @Nullable Object... args)
     */
    public int delete(Long id) {
        //todo: id에 해당하는 customer를 지우고, 해당 쿼리에 영향받는 row 수반환하기
        return jdbcTemplate.update("delete from customers where id = ?", id);
    }

    /**
     * public int update(final PreparedStatementCreator psc, final KeyHolder generatedKeyHolder)
     */
    public Long insertWithKeyHolder(Customer customer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into customers (first_name, last_name) values (?, ?)",
                    new String[]{"id"});
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return id;
    }
}
