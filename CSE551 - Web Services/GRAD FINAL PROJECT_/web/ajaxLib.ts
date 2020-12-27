import $ from 'jquery'

function sendAjaxRequest(ajaxOptions: JQuery.AjaxSettings): Promise<unknown> {
  // Convert jQuery thenables into native Promises
  return Promise.resolve($.ajax(ajaxOptions))
    .catch(({ responseJSON: { Error: error } }) => {
      throw new Error(error)
    })
}

function withRetry<T>(promiseFactory: () => Promise<T>, retryLimit: number = 25): Promise<T> {
  let promise = promiseFactory()

  if (retryLimit > 0) {
    promise = promise.catch(() => withRetry(promiseFactory, retryLimit - 1))
  }

  return promise
}

export {
  sendAjaxRequest,
  withRetry,
}
