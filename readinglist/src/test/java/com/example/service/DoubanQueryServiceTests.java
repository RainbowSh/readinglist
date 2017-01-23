package com.example.service;

import com.example.configuration.DoubanProperties;
import com.example.domain.Book;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by rainbow on 2017/1/22.
 */
@RunWith(SpringRunner.class)
@EnableConfigurationProperties(DoubanProperties.class)
@TestPropertySource("classpath:resource.properties")
public class DoubanQueryServiceTests {

    @Autowired
    private DoubanProperties doubanProperties;
    private DoubanQueryService doubanQueryService;

    @Before
    public void setUp() {
        doubanQueryService = new DoubanQueryService(this.doubanProperties);
    }

    @Test
    public void testQueryBookFromDouban() throws Exception {

        Book expect = new Book();
        expect.setIsbn("9787505715660");
        expect.setAuthor("（法）圣埃克苏佩里");
        expect.setTitle("小王子");
        expect.setPublisher("中国友谊出版公司");
        expect.setImageUrl("https://img3.doubanio.com/mpic/s3294754.jpg");

        Book actual = doubanQueryService.queryByISBN("9787505715660");

        assertThat(actual, sameBeanAs(expect).ignoring("description"));


    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldThrowRuntimeExceptionWhenNotFoundBook() throws Exception {

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("404 Not Found");

        doubanQueryService.queryByISBN("978750571566");

        fail("not throw any exception");

    }

    @Test
    @Ignore
    public void shouldThrowResourceAccessExceptionWhenNetBroken() throws Exception {

        expectedException.expect(ResourceAccessException.class);

        doubanQueryService.queryByISBN("9787505715660");

        fail("not throws ResourceAccessException");

    }
}
