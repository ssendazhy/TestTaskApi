import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import sun.net.www.protocol.http.HttpURLConnection;

public class TestTaskClass {

	public static void main(String[] args) {
		String[] url = {"http://kn-ktapp.herokuapp.com/apitest/accounts",
				"http://kn-ktapp.herokuapp.com/apitest/accounts/12345678"};

		for (int j = 0; j < url.length; j++) {
			try {
				URL obj = new URL(url[j]);
				HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
				connection.setRequestMethod("GET");

				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine = reader.readLine();

				if(!inputLine.contains("[") && !inputLine.contains("]")) {
					inputLine = "[" + inputLine + "]";
				}

				Gson gson = new Gson();
				AccountClass[] accounts = gson.fromJson(inputLine, AccountClass[].class);

				reader.close();
				boolean isEmpt = isAccountIdEmpty(accounts);

				if (!isEmpt) {
					System.out.println("В веб-сервисе " + url[j] + " в одном из счетов не заполнено поле account_id");
				}

			} catch(IllegalStateException e) {
				if(e.getMessage().contentEquals("Expected BEGIN_ARRAY but was BEGIN_OBJECT"));
				{

				}
			} catch(MalformedURLException e){
				e.printStackTrace();
			} catch(IOException e){
				e.printStackTrace();
			}
		}

	}

	public static boolean isAccountIdEmpty(AccountClass[] accounty) {
		for (int i = 0; i < accounty.length; i++) {
			String s = String.valueOf(accounty[i].account_id);
			if (s == null) {
				System.out.println("Пустое значение поля account_id в счету под номером " + i);
				return true;
			}
		}
		return false;
	}

	public class AccountClass {

		String design_url;

		int transactions_total_amount;

		int tariff_avg_month_balance;

		int type;

		String closing_date;

		int partial_withdraw_available;

		int refill_available;

		double blocked_amount;

		String scheme_id;

		String pan;

		int account_id;

		String title_small;

		String title;

		double balance;

		String currency;

		boolean isSalary;

	}

}
