
import com.alibaba.fastjson.JSONObject;
import com.distribute.order.domain.OrderDetails;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wly on 2018/8/30.
 */
public class PostTest {
	public static void main(String[] args) {
		final List<OrderDetails> list = new ArrayList<OrderDetails>(  );
		final Map<String,Object> map = new HashMap<String, Object>(  );
		// 锁住所有线程，等待并发执行
		final CountDownLatch begin = new CountDownLatch(1);


		final ExecutorService exec = Executors.newFixedThreadPool(10);

		for (int index = 0; index < 10; index++)
		{
			final int NO = index + 1;

			Runnable run = new Runnable()
			{
				public void run() {
					try {
						// 等待，所有一起执行
						begin.await();
						//开始模拟登录等待。。。
						System.out.println("No." + NO + " execute");
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					finally {
					}
				}
			};
			exec.submit(run);
		}

		System.out.println("开始执行");
		// begin减一，开始并发执行
		begin.countDown();

		//关闭执行
		exec.shutdown();
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！"+e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return result;
	}

}
